package com.User_Service.User_Service.repository;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.rri.Permission;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("find User By Username")
    public void findUserByUsername(){
        //findUserByUsername(String username)
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.VIEWING);

        userRepository.save(testUser);

        Optional<User> testUser2 = userRepository.findUserByUsername("UserNameTest");
        Assertions.assertEquals(testUser,testUser2.get());
    }

    @Test
    @DisplayName("find User By Email")
    public void findUserByEmail(){
        //findUserByEmail(String email)

        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.VIEWING);

        userRepository.save(testUser);

        Optional<User> testUser2 = userRepository.findUserByEmail("email@test.com");
        Assertions.assertEquals(testUser,testUser2.get());
    }

    @ParameterizedTest
    @DisplayName("find User By Id")
    public void findUserById(UUID userID){
        
    }

    @Test
    @DisplayName("update Permission")
    public void updatePermission(@Param("id")UUID userID, @Param("perm") Permission perm){
        //updatePermission(@Param("id")UUID userID, @Param("perm") Permission perm)
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.VIEWING);

        userRepository.save(testUser);


    }

    @Test
    @DisplayName("update Password")
    public void updatePassword(@Param("id")UUID userID, @Param("newpass")String newpass){
        //updatePassword(@Param("id")UUID userID, @Param("newpass")String newpass)
    }
}
