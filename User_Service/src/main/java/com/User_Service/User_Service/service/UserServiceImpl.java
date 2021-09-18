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
    private final EmailConfig config;

    private final boolean mock = false;

    public UserServiceImpl(EmailConfig config) {
        this.config = config;
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
     * This function verifies the user's authenticity.
     * @param request This class contains the information of the user.
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
     * @param request This class contains the information of the user.
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

            String emailText = "Thank you for signing up to IDIS. Your verification code is:\n";
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
     * @return This class will contain if the password reset process was successful.
     */
    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) throws InvalidRequestException, InvalidKeySpecException, NoSuchAlgorithmException {
        if(request == null) {
            throw new InvalidRequestException("The resetPassword request is null");
        }
        if(request.getEmail() == null || request.getNewPassword() == null) {
            throw new InvalidRequestException("Reset password request contains null");
        }

        //check if the emails exists
        Optional<User> usersByEmail = repository.findUserByEmail(request.getEmail());
        if(usersByEmail.isEmpty()) {
            return new ResetPasswordResponse(false, "Email does not exist");
        }
        else {
            String newPassword = request.getNewPassword();
            UUID id = usersByEmail.get().getId();
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
            if(passUpdate == 0) {
                return new ResetPasswordResponse(false, "Password not updated");
            }
            else {
                return new ResetPasswordResponse(true, "Password successfully updated");
            }
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
     *
     * @param hex
     * @return
     * @throws NoSuchAlgorithmException
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
