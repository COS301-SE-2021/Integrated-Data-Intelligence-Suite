package com.User_Service.User_Service.repository;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.rri.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT s FROM users s WHERE s.username = ?1")
    Optional<User> findUserByUsername(String username);

    @Query("SELECT s FROM users s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Modifying
    @Query("UPDATE users u SET u.permission = :perm WHERE u.id = :id")
    int updatePermission(@Param("id")UUID userID, @Param("perm")Permission perm);

    @Modifying
    @Query("UPDATE users u SET u.password = :newpass WHERE u.id = :id")
    int updatePassword(@Param("id")UUID userID, @Param("newpass")String newpass);
}
