package net.engineeringfaizan.journalApp.service;

import net.engineeringfaizan.journalApp.entity.User;
import net.engineeringfaizan.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @ParameterizedTest
    //we need to replace csvsource with  value source becaue there is no need of csv source
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testfindbyUsername(User user){
            assertTrue(userService.saveNewUser(user));
    }


    //This is for example
    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "10,10,10"
    })
    public void test(int a ,int b , int expected){
        assertEquals(expected , a+b);
    }
}
