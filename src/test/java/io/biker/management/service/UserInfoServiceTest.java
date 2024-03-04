package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import io.biker.management.auth.entity.UserInfo;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserInfoRepo;
import io.biker.management.auth.service.UserInfoServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserInfoServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        UserInfoServiceImpl service() {
            return new UserInfoServiceImpl();
        }
    }

    @MockBean
    private UserInfoRepo repo;

    @Autowired
    private UserInfoServiceImpl service;

    private static UserInfo user;

    @BeforeAll
    public static void setUp() {
        user = new UserInfo(1, new Admin(1, "Durge", "Bhaal@gmail.com", "+666 9772223918", "password"),
                "ADMIN");
    }

    @Test
    public void loadUserByUsername_Existant() {
        when(repo.findByUser_Email(user.getUser().getEmail())).thenReturn(Optional.of(user));

        assertEquals(user.getUser().getEmail(),
                service.loadUserByUsername(user.getUser().getEmail()).getUsername());
    }

    @Test
    public void loadUserByUsername_NonExistant() {
        when(repo.findByUser_Email("Blah")).thenReturn(Optional.empty());

        CustomAuthException ex = assertThrows(CustomAuthException.class,
                () -> {
                    service.loadUserByUsername("Blah");
                });
        assertTrue(ex.getMessage().contains(AuthExceptionMessages.USER_DOES_NOT_EXIST));
    }

    @Test
    public void addUser() {
        when(repo.save(user)).thenReturn(user);

        assertEquals(user, service.addUser(user));
    }

    @Test
    public void deleteUser_Existant() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));

        service.deleteUser(user.getUserId());

        verify(repo, times(1)).deleteById(user.getUserId());
    }

    @Test
    public void deleteUser_NonExistant() {
        when(repo.findById(user.getUserId() - 1)).thenReturn(Optional.empty());

        CustomAuthException ex = assertThrows(CustomAuthException.class,
                () -> {
                    service.deleteUser(user.getUserId() - 1);
                });
        assertTrue(ex.getMessage().contains(AuthExceptionMessages.USER_DOES_NOT_EXIST));

        verify(repo, times(0)).deleteById(user.getUserId() - 1);
    }
}
