package repository;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.repository.UserRepository;
import com.User_Service.User_Service.rri.Permission;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("find User By Username")
    public void findUserByUsername(){
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
    @ValueSource(strings = "d043e930-7b3b-48e3-bdbe-5a3ccfb833db")
    @DisplayName("find User By Id")
    public void findUserById(){
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.VIEWING);

        userRepository.save(testUser);


        Optional<User> testUser2 = userRepository.findUserById(testUser.getId());
        Assertions.assertEquals(testUser,testUser2.get());
    }

    @ParameterizedTest
    @ValueSource(strings = "d043e930-7b3b-48e3-bdbe-5a3ccfb833db")
    @DisplayName("update Permission")
    public void updatePermission(){
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.VIEWING);

        userRepository.save(testUser);

        int testResult = userRepository.updatePermission(testUser.getId(),Permission.IMPORTING);

        Assertions.assertEquals(1,testResult);
    }

    @ParameterizedTest
    @ValueSource(strings = "d043e930-7b3b-48e3-bdbe-5a3ccfb833db")
    @DisplayName("update Password")
    public void updatePassword(){
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.VIEWING);

        userRepository.save(testUser);

        int testResult = userRepository.updatePassword(testUser.getId(),"newPasswordTest");

        Assertions.assertEquals(1,testResult);
    }
}
