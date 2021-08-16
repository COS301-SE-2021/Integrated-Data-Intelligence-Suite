package com.User_Service.User_Service.service;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.exception.InvalidRequestException;
import com.User_Service.User_Service.repository.UserRepository;
import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.rri.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository repository;

    public UserServiceImpl() {

    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }




    /**
     * This function logs the user in.
     * @param request This class contains the user information for login.
     * @return This returns a response contains the exit code**
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
                return new LoginResponse("Successfully logged in", true,user.getId());
            }
            else {
                return new LoginResponse("Incorrect password", false);
            }

        }
    }

    /**
     * This function registers the user to the platform. It creates a new user and stores that user class to the
     * database using persistence. Advanced password security using PBKDF2WithHmacSHA1 algorithm.
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
            System.out.println("repository is null");
        }
        System.out.println(request.getUsername());
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
        //Storing the user in the database
        repository.save(newUser);

        return new RegisterResponse(true, "Registration successful");
    }

    /**
     * This function verifies the user's authenticity.
     * @param request This class contains the information of the user.
     * @return The return class returns if the verification process was successful**
     */
    public VerifyAccountResponse verifyAccount(VerifyAccountRequest request) {
        return null;
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
                return new ResetPasswordResponse(true, "Password successfully updated.");
            }
        }
    }

    /**
     * This function will return the current user logged onto the system.
     * @param request This is the request for the use case.
     * @return This class will contain the current user logged on.
     */
    public GetCurrentUserResponse getCurrentUser(GetCurrentUserRequest request) {
        return null;
    }

    /**
     * This function will allow an admin to manage permissions of users registered
     * to the system.
     * @param request This is the request for the managePermissions use case***
     * @return This is the response for the managePermissions use case***
     */
    @Transactional
    public ManagePersmissionsResponse managePermissions(ManagePermissionsRequest request) throws InvalidRequestException {
        if(request == null) {
            throw new InvalidRequestException("The register request is null");
        }
        if(request.getUsername() == null  || request.getNewPermission() == null) {
            throw new InvalidRequestException("One or more attributes of the register request is null");
        }
        Optional<User> users = repository.findUserByUsername(request.getUsername());
        if(users.isEmpty()) {
            return new ManagePersmissionsResponse("User does not exist", false);
        }
        else {
            User user = users.get();
            int count = repository.updatePermission(user.getId(), request.getNewPermission());
            if(count == 0) {
                return new ManagePersmissionsResponse("Permission for user not updated", false);
            }
            else {
                return new ManagePersmissionsResponse("Permission updated", true);
            }
        }
    }

    /**
     * The purpose of this function is to return a list of all users currently
     * registered to the system.
     * @param request This is the request of the use case.
     * @return This is the response class. It contains a list of all the users
     *         returned from the repository
     * @throws InvalidRequestException This is thrown if the request is null
     *         or if any of its attributes are null.
     */
    @Transactional
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) throws InvalidRequestException {
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
            message = "Returned list of users.";
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
