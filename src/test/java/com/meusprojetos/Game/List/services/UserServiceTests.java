package com.meusprojetos.Game.List.services;

import com.meusprojetos.Game.List.dto.UserDTO;
import com.meusprojetos.Game.List.entities.User;
import com.meusprojetos.Game.List.projections.UserDetailsProjection;
import com.meusprojetos.Game.List.repositories.UserRepository;
import com.meusprojetos.Game.List.tests.UserDetailsFactory;
import com.meusprojetos.Game.List.tests.UserFactory;
import com.meusprojetos.Game.List.utils.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserUtil customUserUtil;

    private List<UserDetailsProjection> userDetailsProjections;
    private User user;
    private UserDTO userDTO;

    private String existingUsername, nonExistingUsername;
    private Long existingUserId, nonExistingUserId;

    @BeforeEach
    void setUp() throws Exception {

        existingUserId = 1L;
        nonExistingUserId = 2L;

        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";

        user = UserFactory.createCustomClientUser(1L, existingUsername);
        userDetailsProjections = UserDetailsFactory.createCustomAdmintUser(existingUsername);

        userDTO = new UserDTO(user);

        Mockito.when(userRepository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetailsProjections);
        Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());

        Mockito.when(userRepository.findByEmail(existingUsername)).thenReturn(user);
        Mockito.when(userRepository.findByEmail(nonExistingUsername)).thenThrow(UsernameNotFoundException.class);
    }

    @Test
    public void loadByUserNameShouldReturnUserDetailsWhenIdExists() {

        UserDetails result = userService.loadUserByUsername(existingUsername);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingUsername);
        Assertions.assertEquals(result.getPassword(), userDetailsProjections.iterator().next().getPassword());
    }

    @Test
    public void loadByUsernameShouldThrowsNotFoundExceptionWhenUserDoesNotExist() {


        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(nonExistingUsername);
        });
    }

    @Test
    public void getMeShouldReturnUserDTOWhenUserAuthenticated() {

        UserService spyUserService = Mockito.spy(userService);
        Mockito.doReturn(user).when(spyUserService).authenticated();

        UserDTO result = spyUserService.getMe();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getEmail(), existingUsername);
        Assertions.assertEquals(result.getId(), userDTO.getId());
        Assertions.assertEquals(result.getRoles(), userDTO.getRoles());
    }

    @Test
    public void getMeShouldThrowsNotFoundWhenUserNotAuthenticated() {

        UserService spyUserService = Mockito.spy(userService);
        Mockito.doThrow(UsernameNotFoundException.class).when(spyUserService).authenticated();

        Assertions.assertThrows(UsernameNotFoundException.class, spyUserService::authenticated);
    }

    @Test
    public void authenticatedShouldReturnWhenUserExists() {

        Mockito.when(customUserUtil.getLoggerUsername()).thenReturn(existingUsername);

        User result = userService.authenticated();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getEmail(), existingUsername);
    }

    @Test
    public void authenticatedShouldThrowsNotFoundExceptionsWhenUserDoesNotExist() {

        Mockito.doThrow(ClassCastException.class).when(customUserUtil).getLoggerUsername();

        Assertions.assertThrows(UsernameNotFoundException.class, userService::authenticated);
    }
}
