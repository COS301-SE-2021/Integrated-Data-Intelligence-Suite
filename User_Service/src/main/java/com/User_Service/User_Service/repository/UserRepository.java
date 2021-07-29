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

    @Modifying
    @Query("UPDATE users u SET u.permission = :perm WHERE u.id = ?1")
    int updatePermission(UUID userID, @Param("perm")Permission perm);
}
