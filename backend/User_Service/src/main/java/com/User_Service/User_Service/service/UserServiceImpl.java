package com.User_Service.User_Service.service;

import com.User_Service.User_Service.config.EmailConfig;
import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.exception.InvalidRequestException;
import com.User_Service.User_Service.repository.UserRepository;
import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.rri.Permission;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.ws.rs.GET;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository repository;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private final EmailConfig config = new EmailConfig();

    private final boolean mock = false;

    public UserServiceImpl() {
    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }


    /**
     * This function logs the user in. The user will not be able to log in to the system
     * if he/she is not verified.
     * @param request This class contains the user information for login.
     *                It contains the email of the user and password set
     *                by the user when registering their account.
     *                email - The email of the user that would like to log in.
     *                password - The password of the user that would like to log in.
     * @return This returns a response containing information if the login attempt
     *         was successful or not.
     */
    public LoginResponse login(LoginRequest request) throws NoSuchAlgorithmException, InvalidRequestException, InvalidKeySpecException {
        if(request == null) {
            throw new InvalidRequestException("The login request is null");
        }
        if(request.getEmail() == null || request.getPassword() == null) {
            throw new InvalidRequestException("The login request contains null values");
        }

        Optional<User> existingUser = repository.findUserByEmail(request.getEmail());
        if(existingUser.isEmpty()) {
            return new LoginResponse("The email does not exist", false);
        }
        else {
            User user = existingUser.get();
            if(!user.getVerified()) {
                return new LoginResponse("This account is not verified. Please verify to get access to the system", false);
            }
            //password validation
            String[] parts = user.getPassword().split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(request.getPassword().toCharArray(), salt, iterations, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for(int i = 0; i < hash.length && i < testHash.length; i++)
            {
                diff |= hash[i] ^ testHash[i];
            }

            if (diff == 0) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", user.getId().toString());
                jsonObject.put("username", user.getUsername());
                jsonObject.put("isAdmin", user.getAdmin());
                jsonObject.put("permission", user.getPermission().toString());
                return new LoginResponse("Successfully logged in", true, jsonObject.toString());
            }
            else {
                return new LoginResponse("Incorrect password", false);
            }

        }
    }

    /**
     * This function registers the user to the platform. It creates a new user and stores that user class to the
     * database using persistence. Advanced password security using PBKDF2WithHmacSHA1 algorithm.
     * It will also send an email notification to the user containing their verification code.
     * @param request This class contains the user information to store the user within the system.
     *                username - The entered username of the new user.
     *                firstName - The first name of the new user.
     *                lastName - The last name of the new user.
     *                password - The entered password of the new user.
     *                email - The email address of the new user.
     * @return This returns a response contains if the registration of the user was successful.
     */
    @Transactional
    public RegisterResponse register(RegisterRequest request) throws InvalidRequestException, InvalidKeySpecException, NoSuchAlgorithmException {
        if(request == null) {
            throw new InvalidRequestException("The register request is null.");
        }
        if(request.getUsername() == null || request.getFirstName() == null || request.getLastName() == null || request.getEmail() == null || request.getPassword() == null) {
            throw new InvalidRequestException("One or more attributes of the register request is null.");
        }

        if(repository == null) {
            System.out.println("Repository is null");
        }

        //email domain check
        if(!config.isAllowAnyDomain()) {
            String[] domain = request.getEmail().split("@");

            if(!domain[1].equals(config.getEmailDomain())) {
                return new RegisterResponse(false, "You are not authorized to register");
            }
        }

        Optional<User> usersByUsername= repository.findUserByUsername(request.getUsername());
        if(usersByUsername.isPresent()) {
            return new RegisterResponse(false, "Username has been taken");
        }

        Optional<User> usersByEmail = repository.findUserByEmail(request.getEmail());
        if(usersByEmail.isPresent()) {
            return new RegisterResponse(false, "This email has already been registered");
        }

        String password = request.getPassword();
        String hashedPass;
        //Hashing the password
        int iterations = 1000;
        char[] chars = password.toCharArray();
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        hashedPass = iterations + ":" + toHex(salt) + ":" + toHex(hash);

        //Creating User
        User newUser = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), hashedPass, Permission.VIEWING);

        //Creating a verification code for the user to verify their account
        String verificationCode = RandomStringUtils.random(64, true, true);

        //Initializing their verified status to false and setting user's verification code
        newUser.setVerified(false);
        newUser.setVerificationCode(verificationCode);

        //Setting date the user registered
        newUser.setDateCreated(new Date());

        //Storing the user in the database
        User checkIfSaved = repository.save(newUser);

        if(checkIfSaved != newUser) {
            return new RegisterResponse(false, "Registration failed");
        }

        if(!mock) { //to be removed before deployment
            String emailText = "Thank you for signing up to IDIS. Your verification code is:\n";
            emailText += newUser.getVerificationCode() + "\n";

            String to = newUser.getEmail();
            String from = "emergenoreply@gmail.com";
            String subject = "Integrated Data Intelligence Suite Registration";

            SendEmailNotificationRequest emailRequest = new SendEmailNotificationRequest(emailText, to, from, subject);

            try {
                CompletableFuture<SendEmailNotificationResponse> emailResponse  = notificationService.sendEmailNotification(emailRequest);
            } catch (Exception e) {
                e.printStackTrace();
                return new RegisterResponse(false, "An error has occurred while sending an the activation code to user. Exception in email sender was thrown.");
            }
        }

        return new RegisterResponse(true, "Registration successful. An email will be sent shortly containing your registration details.");
    }

    /**
     * This function allows a user to request to be an admin. It will send an admin user an
     * email containing the details of the user that wants to request to be an admin.
     * The admin will decide whether or not go through with the request.
     * @param request This class contains the details of the user.
     * @return This class entails if the registration of the user was successful or not.
     * @throws InvalidRequestException This is thrown if the request is invalid.
     * @deprecated To be removed/Not implemented/Does not need to be implemented.
     */
    @Transactional
    public RequestAdminResponse requestAdmin(RequestAdminRequest request) throws InvalidRequestException {
        if(request == null) {
            throw new InvalidRequestException("The request is null");
        }

        if(request.getUsername() == null || request.getFirstName() == null || request.getLastName() == null || request.getEmail() == null || request.getPassword() == null) {
            throw new InvalidRequestException("One or more attributes of the register request is null.");
        }

        Optional<User> usersByEmail = repository.findUserByEmail(request.getEmail());
        if(usersByEmail.isPresent()) {
            User user = usersByEmail.get();

            if(user.getAdmin()) {
                return new RequestAdminResponse(false, "The user is already an admin.");
            }

            String emailText = "A user has requested to be an admin. Please verify their details.\n";
            emailText += "Name: " + user.getFirstName() + "\n";
            emailText += "Surname: " + user.getLastName() + "\n";
            emailText += "Email: " + user.getEmail() + "\n";
            emailText += "Username: " + user.getUsername() + "\n";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("emergenoreply@gmail.com");
            message.setSubject("Admin Request");
            message.setText(emailText);

            ArrayList<User> adminUsers = repository.findUsersByAdmin();
            ArrayList<String> admins = new ArrayList<String>();
            String[] adminEmails = new String[adminUsers.size()];
            if(adminUsers.isEmpty()) {
                message.setTo("shreymandalia@gmail.com");
            }
            else {
                for (User adminUser : adminUsers) {
                    admins.add(adminUser.getEmail());
                }
                adminEmails = admins.toArray(adminEmails);
                message.setTo(adminEmails);
            }
            //emailSender.send(message);
            return new RequestAdminResponse(true, "Registration as admin successful. Your admin status will be updated when a current admin has verified your credentials.");
        }
        else {
            return new RequestAdminResponse(false, "The user does not exist");
        }

    }

    /**
     * This method will update the details of a specific user.
     * @param request This class contains the details of the user.
     *                username - The new/old username of the user.
     *                firstName - The new/old first name of the user.
     *                lastName - The new/old last name of the user.
     *                email - The new/old email address of the user.
     *                id - The UUID of the user to ensure the user exists.
     * @return This class will contain if updating the user's details was
     *         successful or not.
     * @throws Exception Thrown if an error was encountered during the request.
     */
    @Transactional
    public UpdateProfileResponse updateProfile(UpdateProfileRequest request) throws Exception{
        if(request == null) {
            throw new InvalidRequestException("The request is null");
        }

        if(request.getEmail() == null || request.getLastName() == null || request.getFirstName() == null || request.getUsername() == null) {
            throw new InvalidRequestException("Some or all of the request values are null");
        }

        Optional<User> userCheck = repository.findUserById(UUID.fromString(request.getId()));

        if(userCheck.isEmpty()) {
            return new UpdateProfileResponse(false, "User does not exist");
        }
        else {
            User u = userCheck.get();

            boolean usernameUpdated = true;
            boolean firstNameUpdated = true;
            boolean lastNameUpdated = true;
            boolean emailUpdated = true;

            if(!u.getUsername().equals(request.getUsername())) {
                int count = repository.updateUsername(u.getId(), request.getUsername());

                if(count != 1) {
                    usernameUpdated = false;
                }
            }

            if(!u.getFirstName().equals(request.getFirstName())) {
                int count = repository.updateFirstName(u.getId(), request.getFirstName());

                if(count != 1) {
                    firstNameUpdated = false;
                }
            }

            if(u.getLastName().equals(request.getLastName())) {
                int count = repository.updateLastName(u.getId(), request.getLastName());

                if(count != 1) {
                    lastNameUpdated = false;
                }
            }

            if(u.getEmail().equals(request.getEmail())) {
                int count = repository.updateEmail(u.getId(), request.getEmail());

                if(count != 1) {
                    emailUpdated = false;
                }
            }

            if(!usernameUpdated || !firstNameUpdated || !lastNameUpdated || !emailUpdated) {
                return new UpdateProfileResponse(false, "Failed to update account");
            }
            else {
                return new UpdateProfileResponse(true, "Successfully updated account");
            }
        }
    }

    /**
     * This function verifies the user's authenticity.
     * @param request This class contains the information of the user.
     *                email - The email address of the user that wants to verify
     *                        their account.
     *                verificationCode - The verification code sent to the user
     *                                   via email.
     * @return The return class returns if the verification process was successful**
     */
    @Transactional
    public VerifyAccountResponse verifyAccount(VerifyAccountRequest request) throws Exception {
        if(request == null) {
            throw new InvalidRequestException("The request is null.");
        }

        Optional<User> userCheck = repository.findUserByEmail(request.getEmail());

        if(userCheck.isEmpty()) {
            return new VerifyAccountResponse(false, "User does not exist");
        }
        else {
            User user = userCheck.get();

            if(user.getVerified()) {
                return new VerifyAccountResponse(false, "This account has already been verified");
            }

            if(request.getVerificationCode().equals(user.getVerificationCode())) {
                int success = repository.verifyUser(user.getId());
                if(success == 0) {
                    return new VerifyAccountResponse(false, "Unable to verify account");
                }
                else {
                    return new VerifyAccountResponse(true, "Successfully verified account");
                }
            }
            else {
                return new VerifyAccountResponse(false, "Verification code is incorrect");
            }
        }
    }

    /**
     * This function will resend the verification code to user.
     * @param request This class contains the email address of the user.
     *                email - The email address that will be used to send the
     *                        verification code to.
     * @return The return class returns if the verification process was successful**
     */
    @Transactional
    public ResendCodeResponse resendCode(ResendCodeRequest request) throws Exception {
        if(request == null) {
            throw new InvalidRequestException("The request is null");
        }

        if(request.getEmail() == null) {
            throw new InvalidRequestException("The request email is null");
        }

        Optional<User> userCheck = repository.findUserByEmail(request.getEmail());

        if(userCheck.isEmpty()) {
            return new ResendCodeResponse(false, "User does not exist");
        }
        else {
            User user = userCheck.get();

            String emailText = "Your verification code is:\n";
            emailText += user.getVerificationCode();
            String to = user.getEmail();
            String from = "emergenoreply@gmail.com";
            String subject = "IDIS Verification Code";

            SendEmailNotificationRequest emailRequest = new SendEmailNotificationRequest(emailText, to, from, subject);

            try {
                CompletableFuture<SendEmailNotificationResponse> emailResponse  = notificationService.sendEmailNotification(emailRequest);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResendCodeResponse(false, "An error has occurred while sending an the verification code to user");
            }

            return new ResendCodeResponse(true, "Verification code sent");
        }
    }

    /**
     * This function will allow the user to reset their password and store the new password
     * in the database.
     * @param request This class will contain the new password of the user.
     *                newPassword - The new password for that user.
     *                email - The email of the user that requested password change.
     *                otp - The one-time-password sent to the user via email.
     * @return This class will contain if the password reset process was successful.
     */
    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) throws InvalidRequestException, InvalidKeySpecException, NoSuchAlgorithmException {
        if(request == null) {
            throw new InvalidRequestException("The resetPassword request is null");
        }
        if(request.getEmail() == null || request.getNewPassword() == null || request.getOtp() == null) {
            if(request.getOtp() == null) {
                System.out.println("OTP is null");
            }
            if(request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
                System.out.println("PAssword is null");
            }
            if(request.getEmail() == null) {
                System.out.println("email is null");
            }
            throw new InvalidRequestException("Reset password request contains null");
        }

        //check if the emails exists
        Optional<User> usersByEmail = repository.findUserByEmail(request.getEmail());
        if(usersByEmail.isEmpty()) {
            return new ResetPasswordResponse(false, "Email does not exist");
        }
        else {
            User user = usersByEmail.get();

            if(request.getOtp().isEmpty()) {
                return new ResetPasswordResponse(false, "Incorrect OTP");
            }

            if(!request.getOtp().matches("[0-9]+") || request.getOtp().length() > 6) {
                return new ResetPasswordResponse(false, "Invalid OTP");
            }

            if(!request.getOtp().equals(user.getPasswordOTP())) {
                return new ResetPasswordResponse(false, "The OTP does not match");
            }

            String newPassword = request.getNewPassword();
            UUID id = user.getId();
            //hash new password
            String hashedPass;
            int iterations = 1000;
            char[] chars = newPassword.toCharArray();
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            hashedPass = iterations + ":" + toHex(salt) + ":" + toHex(hash);
            int passUpdate = repository.updatePassword(id, hashedPass);
            repository.updatePasswordOTP(user.getId(), "");
            if(passUpdate == 0) {
                return new ResetPasswordResponse(false, "Password not updated");
            }
            else {
                return new ResetPasswordResponse(true, "Password successfully updated");
            }
        }
    }

    /**
     * This function will allow the user to reset their password and store the new password
     * in the database.
     * @param request This class will contain the new password of the user.
     *                email - The email which to send the OTP to.
     * @return This class will contain if the password reset process was successful.
     */
    @Transactional
    public ResendCodeResponse sendOTP(ResendCodeRequest request) throws InvalidRequestException, InvalidKeySpecException, NoSuchAlgorithmException {
        if(request == null) {
            throw new InvalidRequestException("The resetPassword request is null");
        }
        if(request.getEmail() == null) {
            throw new InvalidRequestException("Reset password request contains null");
        }

        //check if the emails exists
        Optional<User> usersByEmail = repository.findUserByEmail(request.getEmail());
        if(usersByEmail.isEmpty()) {
            return new ResendCodeResponse(false, "Email does not exist");
        }
        else {
            User user = usersByEmail.get();

            //Generate 6 digit OTP
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            String otp = String.format("%06d", number);

            String emailText = "Your OTP to change your password is:\n";
            emailText += otp;
            String to = user.getEmail();
            String from = "emergenoreply@gmail.com";
            String subject = "IDIS Password OTP";

            repository.updatePasswordOTP(user.getId(), otp);

            SendEmailNotificationRequest emailRequest = new SendEmailNotificationRequest(emailText, to, from, subject);

            try {
                CompletableFuture<SendEmailNotificationResponse> emailResponse  = notificationService.sendEmailNotification(emailRequest);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResendCodeResponse(false, "An error has occurred while sending the OTP");
            }

            return new ResendCodeResponse(true, "Password OTP sent");
        }
    }

    /**
     * This function will return the current user logged onto the system.
     * @param request This is the request for the use case.
     * @return This class will contain the current user logged on.
     */
    public GetCurrentUserResponse getCurrentUser(GetCurrentUserRequest request) throws InvalidRequestException {
        if(request == null) {
            throw new InvalidRequestException("The request is null");
        }
        if(request.getId() == null) {
            throw new InvalidRequestException("The request contains null");
        }

        Optional<User> currentUser = repository.findUserById(UUID.fromString(request.getId()));

        if(currentUser.isPresent()) {
            return new GetCurrentUserResponse(true, "Successfully returned current user", currentUser.get().getFirstName(), currentUser.get().getLastName(), currentUser.get().getUsername(), currentUser.get().getEmail(), currentUser.get().getAdmin());
        }
        else {
            return new GetCurrentUserResponse(false, "User does not exist");
        }
    }

    /**
     * This function will allow an admin to manage permissions of users registered
     * to the system.
     * @param request This is the request for the changeUser use case***
     * @return This is the response for the changeUser use case***
     */
    @Transactional
    public ChangeUserResponse changeUser(ChangeUserRequest request) throws InvalidRequestException {
        if(request == null) {
            throw new InvalidRequestException("The Manage Permissions request is null");
        }
        if(request.getUsername() == null  || request.getNewPermission() == null) {
            throw new InvalidRequestException("One or more attributes of the register request is null");
        }
        Optional<User> users = repository.findUserByUsername(request.getUsername());
        if(users.isEmpty()) {
            return new ChangeUserResponse("User does not exist", false);
        }
        else {
            User user = users.get();
            int count = repository.updatePermission(user.getId(), request.getNewPermission());
            repository.updateAdmin(user.getId(), request.isAdmin());
            if(count == 0) {
                return new ChangeUserResponse("Permission for user not updated", false);
            }
            else {
                return new ChangeUserResponse("Permission updated", true);
            }
        }
    }

    /**
     * The purpose of this function is to return a list of all users currently
     * registered to the system.
     * @return This is the response class. It contains a list of all the users
     *         returned from the repository
     * @throws InvalidRequestException This is thrown if the request is null
     *         or if any of its attributes are null.
     */
    @Transactional
    public GetAllUsersResponse getAllUsers() throws InvalidRequestException {
//        if(request == null) {
//            throw new InvalidRequestException("The request is null");
//        }
        boolean success;
        String message;
        List<User> users = repository.findAll();
        if(users.isEmpty()) {
            message = "There are no registered users";
            success = false;
        }
        else {
            message = "Returned list of users";
            success = true;
        }
        return new GetAllUsersResponse(message, success, users);
    }

    public GetUserResponse getUser(GetUserRequest request) throws InvalidRequestException{

        if(request == null ) throw new InvalidRequestException("get user request is null");

        if(request.getId() == null) throw new InvalidRequestException("user id is null");

        boolean success;
        String message;
        List<User> response = repository.findAllById(Collections.singleton(request.getId()));
        if(response.isEmpty()){
            message = "No user with specified id";
            success = false;
        }else{
            message = "Returned user";
            success = true;
            System.out.println(response.get(0).getFirstName());
        }
        System.out.println(message);
        return  new GetUserResponse(message, success, response);
    }

    /**
     * This method will add a report id to user's list of reports for a user.
     * @param request This will contain the id of the user and id of the report.
     * @return This will contain the response stating if the request was successful or not
     * @throws Exception This will be thrown if an error has been encountered during execution.
     */
    @Transactional
    public ReportResponse addReport(ReportRequest request) throws Exception {
        if(request == null || request.getReportID() == null || request.getUserID() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        else {
            if(request.getReportID().equals("") || request.getUserID().equals("")) {
                throw new InvalidRequestException("The request contains empty values");
            }

            //Find user by ID
            Optional<User> userExists = repository.findUserById(UUID.fromString(request.getUserID()));
            //Check if user exists
            if(userExists.isPresent()) {
                User current = userExists.get();
                //Add report id for the user
                current.addReportID(request.getReportID());
                //Return response
                return new ReportResponse(true, "Added report");
            }
            else {
                return new ReportResponse(false, "User does not exist");
            }
        }
    }

    /**
     * This method will add a report id to user's list of reports for a user.
     * @param request This will contain the id of the user and id of the report.
     * @return This will contain the response stating if the request was successful or not
     * @throws Exception This will be thrown if an error has been encountered during execution.
     */
    @Transactional
    public ReportResponse removeReport(ReportRequest request) throws Exception {
        if(request == null || request.getReportID() == null || request.getUserID() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        else {
            if(request.getReportID().equals("") || request.getUserID().equals("")) {
                throw new InvalidRequestException("The request contains empty values");
            }

            //Find user by ID
            Optional<User> userExists = repository.findUserById(UUID.fromString(request.getUserID()));
            //Check if user exists
            if(userExists.isPresent()) {
                User current = userExists.get();
                //Add report id for the user
                current.removeReportID(request.getReportID());
                //Return response
                return new ReportResponse(true, "Successfully removed report");
            }
            else {
                return new ReportResponse(false, "User does not exist");
            }
        }
    }

    /**
     * This method will return a list of reports saved by the user.
     * @param request This will contain the id of the user.
     * @return This will contain the response stating if the request was successful or not
     * and it will contain a list of report IDs.
     * @throws Exception This will be thrown if an error has been encountered during execution.
     */
    @Transactional
    public GetUserReportsResponse getReports(GetUserReportsRequest request) throws Exception {
        if(request == null || request.getId() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        else {
            if(request.getId().equals("")) {
                throw new InvalidRequestException("The request contains empty values");
            }

            //Find user by ID
            Optional<User> userExists = repository.findUserById(UUID.fromString(request.getId()));
            //Check if user exists
            if(userExists.isPresent()) {
                User current = userExists.get();
                //Return response
                return new GetUserReportsResponse(true, "Retrieved report IDs", current.getReportIDs());
            }
            else {
                return new GetUserReportsResponse(false, "User does not exist", null);
            }
        }
    }

    /**
     * This method will return a list of models saved by the user.
     * @param request This will contain the id of the user.
     * @return This will contain the response stating if the request was successful or not
     * and it will contain a list of report IDs.
     * @throws Exception This will be thrown if an error has been encountered during execution.
     */
    @Transactional
    public GetModelsResponse getModels(GetModelsRequest request) throws Exception {
        if(request == null || request.getUserID() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        else {
            if(request.getUserID().equals("")) {
                throw new InvalidRequestException("The request contains empty values");
            }

            //Find user by ID
            Optional<User> userExists = repository.findUserById(UUID.fromString(request.getUserID()));
            //Check if user exists
            if(userExists.isPresent()) {
                User current = userExists.get();
                //Return response
                return new GetModelsResponse(true, "Retrieved models", current.getModels());
            }
            else {
                return new GetModelsResponse(false, "User does not exist", null);
            }
        }
    }

    /**
     * This method will add a model to user's list of models for a user.
     * @param request This will contain the id of the user and id of the report.
     * @return This will contain the response stating if the request was successful or not
     * @throws Exception This will be thrown if an error has been encountered during execution.
     */
    @Transactional
    public ModelResponse addModel(ModelRequest request) throws Exception {
        if(request == null || request.getModelID() == null || request.getUserID() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        else {
            if(request.getModelID().equals("") || request.getUserID().equals("")) {
                throw new InvalidRequestException("The request contains empty values");
            }

            //Find user by ID
            Optional<User> userExists = repository.findUserById(UUID.fromString(request.getUserID()));
            //Check if user exists
            if(userExists.isPresent()) {
                User current = userExists.get();
                //Add report id for the user
                current.addModel(request.getModelID());
                //Return response
                return new ModelResponse(true, "Added model");
            }
            else {
                return new ModelResponse(false, "User does not exist");
            }
        }
    }

    /**
     * This method will add a model to user's list of models for a user.
     * @param request This will contain the id of the user and id of the report.
     * @return This will contain the response stating if the request was successful or not
     * @throws Exception This will be thrown if an error has been encountered during execution.
     */
    @Transactional
    public ModelResponse removeModel(ModelRequest request) throws Exception {
        if(request == null || request.getModelID() == null || request.getUserID() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        else {
            if(request.getModelID().equals("") || request.getUserID().equals("")) {
                throw new InvalidRequestException("The request contains empty values");
            }

            //Find user by ID
            Optional<User> userExists = repository.findUserById(UUID.fromString(request.getUserID()));
            //Check if user exists
            if(userExists.isPresent()) {
                User current = userExists.get();
                //Add report id for the user
                current.removeModel(request.getModelID());
                //Return response
                return new ModelResponse(true, "Added model");
            }
            else {
                return new ModelResponse(false, "User does not exist");
            }
        }
    }

    /**
     * This method will add a model to user's list of models for a user.
     * @param request This will contain the id of the user and id of the report.
     * @return This will contain the response stating if the request was successful or not
     * @throws Exception This will be thrown if an error has been encountered during execution.
     */
    @Transactional
    public ModelResponse selectModel(ModelRequest request) throws Exception {
        if(request == null || request.getModelID() == null || request.getUserID() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        else {
            if(request.getModelID().equals("") || request.getUserID().equals("")) {
                throw new InvalidRequestException("The request contains empty values");
            }

            //Find user by ID
            Optional<User> userExists = repository.findUserById(UUID.fromString(request.getUserID()));
            //Check if user exists
            if(userExists.isPresent()) {
                User current = userExists.get();
                //Add report id for the user
                current.selectModel(request.getModelID());
                //Return response
                return new ModelResponse(true, "Selected model");
            }
            else {
                return new ModelResponse(false, "User does not exist");
            }
        }
    }

    /**
     * This method will add a model to user's list of models for a user.
     * @param request This will contain the id of the user and id of the report.
     * @return This will contain the response stating if the request was successful or not
     * @throws Exception This will be thrown if an error has been encountered during execution.
     */
    @Transactional
    public ModelResponse deselectModel(ModelRequest request) throws Exception {
        if(request == null || request.getModelID() == null || request.getUserID() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        else {
            if(request.getModelID().equals("") || request.getUserID().equals("")) {
                throw new InvalidRequestException("The request contains empty values");
            }

            //Find user by ID
            Optional<User> userExists = repository.findUserById(UUID.fromString(request.getUserID()));
            //Check if user exists
            if(userExists.isPresent()) {
                User current = userExists.get();
                //Add report id for the user
                current.deselectModel(request.getModelID());
                //Return response
                return new ModelResponse(true, "Deselected model");
            }
            else {
                return new ModelResponse(false, "User does not exist");
            }
        }
    }

/*
=================== Private Functions ====================
 */
    /**
     * This is an internal function converts a byte array to a hex string.
     * @param array This is the byte array that will be converted to a hex string.
     * @return This is the converted byte array in a string format.
     * @throws NoSuchAlgorithmException Thrown if the algorithm does not exist.
     */
    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    /**
     *This is an internal functions that converts a hex string into a byte array.
     * @param hex The hex string that will be converted.
     * @return The converted byte array.
     * @throws NoSuchAlgorithmException Thrown if the algorithm does not exist.
     */
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
