package com.User_Service.User_Service.repository;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.rri.Permission;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Fetch AIModel by type")
    public void findUserByUsername(){
        //findUserByUsername(String username)
    }

    @Test
    @DisplayName("find User By Email")
    public void findUserByEmail(String email){
        //findUserByEmail(String email)
    }

    @Test
    @DisplayName("find User By Id")
    public void findUserById(UUID userID){
        //findUserById(UUID userID)
    }

    @Test
    @DisplayName("update Permission")
    public void updatePermission(@Param("id")UUID userID, @Param("perm") Permission perm){
        //updatePermission(@Param("id")UUID userID, @Param("perm") Permission perm)
    }

    @Test
    @DisplayName("update Password")
    public void updatePassword(@Param("id")UUID userID, @Param("newpass")String newpass){
        //updatePassword(@Param("id")UUID userID, @Param("newpass")String newpass)
    }
}
