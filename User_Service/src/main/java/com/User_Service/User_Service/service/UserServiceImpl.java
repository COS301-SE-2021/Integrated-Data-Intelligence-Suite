package com.User_Service.User_Service.service;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.exception.InvalidRequestException;
import com.User_Service.User_Service.repository.UserRepository;
import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository repository;

    public UserServiceImpl() {

    }

    /**
     * This function logs the user in.
     * @param request This class contains the user information for login.
     * @return This returns a response contains the exit code**
     */
    public LoginResponse login(LoginRequest request) {
        return null;
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
        User newUser = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), hashedPass, request.getPermission());
        //Storing the user in the database
        repository.save(newUser);

        return new RegisterResponse(true, "Registration successful");
    }

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
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        return null;
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
            throw new InvalidRequestException("The register request is null.");
        }
        if(request.getUsername() == null  || request.getNewPermission() == null) {
            throw new InvalidRequestException("One or more attributes of the register request is null.");
        }
        Optional<User> users = repository.findUserByUsername(request.getUsername());
        if(users.isEmpty()) {
            return new ManagePersmissionsResponse("User does not exist.", false);
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
}
