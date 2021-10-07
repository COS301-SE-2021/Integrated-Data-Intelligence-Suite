package com.User_Service.User_Service.repository;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.rri.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT s FROM users s WHERE s.username = ?1")
    Optional<User> findUserByUsername(String username);

    @Query("SELECT s FROM users s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT s FROM users s WHERE s.id = ?1")
    Optional<User> findUserById(UUID userID);

    @Query("SELECT s FROM users s WHERE s.isAdmin = true")
    ArrayList<User> findUsersByAdmin();

    @Query("SELECT s FROM users s WHERE s.isVerified = false ORDER BY s.dateCreated ASC")
    ArrayList<User> findAllByIsVerifiedFalseOrderByDateCreatedAsc();

    @Modifying
    @Query("UPDATE users u SET u.permission = :perm WHERE u.id = :id")
    int updatePermission(@Param("id")UUID userID, @Param("perm")Permission perm);

    @Modifying
    @Query("UPDATE users u SET u.password = :newpass WHERE u.id = :id")
    int updatePassword(@Param("id")UUID userID, @Param("newpass")String newpass);

    @Modifying
    @Query("UPDATE users u SET u.isVerified = true WHERE u.id = :id")
    int verifyUser(@Param("id")UUID userid);

    @Modifying
    @Query("UPDATE users u SET u.isAdmin = :newAdmin WHERE u.id = :id")
    int updateAdmin(@Param("id")UUID userid, @Param("newAdmin") boolean newAdmin);

    @Modifying
    @Query("UPDATE users u SET u.passwordOTP = :newOTP WHERE u.id = :id")
    int updatePasswordOTP(@Param("id")UUID userid, @Param("newOTP") String newOTP);

    @Modifying
    @Query("UPDATE users u SET u.firstName = :newFirst WHERE u.id = :id")
    int updateFirstName(@Param("id")UUID userid, @Param("newFirst") String newFirst);

    @Modifying
    @Query("UPDATE users u SET u.lastName = :newLast WHERE u.id = :id")
    int updateLastName(@Param("id")UUID userid, @Param("newLast") String newLast);

    @Modifying
    @Query("UPDATE users u SET u.username = :newUsername WHERE u.id = :id")
    int updateUsername(@Param("id")UUID userid, @Param("newUsername") String newUsername);

    @Modifying
    @Query("UPDATE users u SET u.email = :newEmail WHERE u.id = :id")
    int updateEmail(@Param("id")UUID userid, @Param("newEmail") String newEmail);
}
