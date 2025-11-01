package com.briannalytical.wealthcore;

import com.briannalytical.wealthcore.Model.Entity.User;
import com.briannalytical.wealthcore.Repository.UserRepository;
import com.briannalytical.wealthcore.Service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

}

