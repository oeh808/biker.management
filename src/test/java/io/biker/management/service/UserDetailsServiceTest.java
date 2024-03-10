package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.biker.management.admin.entity.Admin;
import io.biker.management.auth.entity.UserRoles;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserRolesRepo;
import io.biker.management.auth.service.UserDetailsServiceImpl;
import io.biker.management.constants.Roles_Const;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserDetailsServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        UserDetailsServiceImpl service() {
            return new UserDetailsServiceImpl();
        }
    }

    @MockBean
    private UserRolesRepo repo;

    @Autowired
    private UserDetailsServiceImpl service;

    private static UserRoles userRoles;
    private static List<String> roles;

    @BeforeAll
    public static void setUp() {
        roles = new ArrayList<>();
        roles.add(Roles_Const.ADMIN);

        userRoles = new UserRoles(1, new Admin(1, "Durge", "Bhaal@gmail.com", "+666 9772223918", "password"),
                roles);
    }

    @Test
    public void loadUserByUsername_Existant() {
        when(repo.findByUser_Email(userRoles.getUser().getEmail())).thenReturn(Optional.of(userRoles));

        assertEquals(userRoles.getUser().getEmail(),
                service.loadUserByUsername(userRoles.getUser().getEmail()).getUsername());
    }

    @Test
    public void loadUserByUsername_NonExistant() {
        when(repo.findByUser_Email("Blah")).thenReturn(Optional.empty());

        CustomAuthException ex = assertThrows(CustomAuthException.class,
                () -> {
                    service.loadUserByUsername("Blah");
                });
        assertTrue(ex.getMessage().contains(AuthExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD));
    }
}
