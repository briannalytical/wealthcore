package com.briannalytical.wealthcore;

import com.briannalytical.wealthcore.Model.Entity.User;
import com.briannalytical.wealthcore.Model.Enum.UserRole;
import com.briannalytical.wealthcore.Repository.UserRepository;
import com.briannalytical.wealthcore.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("basedgod420");
        testUser.setPassword("encodedPassword");
        testUser.setEmail("nicholasdeorio@youtube.com");
        testUser.setRole(UserRole.USER);
        testUser.setEnabled(true);
    }

    @Test
    public void registerUser_ShouldThrowException_WhenUserNameExists() {
        // arrange
        String username = "existinguser";
        String password = "password123";
        String email = "new@example.com";

        Optional<User> userInBox = Optional.of(testUser);
        when(userRepository.findByUsername(username)).thenReturn(userInBox);

        // act and assert
        try {
            userService.registerUser(username, password, email);
            fail("Should have thrown an exception!");
        } catch (RuntimeException e) {
            assertEquals("Username already exists", e.getMessage());
        }
    }

}

