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
import io.biker.management.auth.entity.UserRoles;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserRolesRepo;
import io.biker.management.auth.service.UserRolesServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
// FIXME: Update tests
public class UserRolesServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        UserRolesServiceImpl service(UserRolesRepo repo) {
            return new UserRolesServiceImpl(repo, null, null, null, null);
        }
    }

    @MockBean
    private UserRolesRepo repo;

    @Autowired
    private UserRolesServiceImpl service;

    private static UserRoles userRoles;

    @BeforeAll
    public static void setUp() {
        userRoles = new UserRoles(1, new Admin(1, "Durge", "Bhaal@gmail.com", "+666 9772223918", "password"),
                "ADMIN");
    }

    @Test
    public void addUser_Successful() {
        when(repo.save(userRoles)).thenReturn(userRoles);

        assertEquals(userRoles, service.addUser(userRoles));
    }

    @Test
    public void addUser_DuplicateData() {
        when(repo.findByUser_Email(userRoles.getUser().getEmail())).thenReturn(Optional.of(userRoles));
        when(repo.findByUser_PhoneNumber(userRoles.getUser().getPhoneNumber())).thenReturn(Optional.of(userRoles));

        CustomAuthException ex = assertThrows(CustomAuthException.class,
                () -> {
                    service.addUser(userRoles);
                });
        assertTrue(ex.getMessage().contains(AuthExceptionMessages.DUPLICATE_DATA));
    }

    @Test
    public void deleteUser_Existant() {
        when(repo.findById(userRoles.getUserId())).thenReturn(Optional.of(userRoles));

        service.deleteUser(userRoles.getUserId());

        verify(repo, times(1)).deleteById(userRoles.getUserId());
    }

    @Test
    public void deleteUser_NonExistant() {
        when(repo.findById(userRoles.getUserId() - 1)).thenReturn(Optional.empty());

        CustomAuthException ex = assertThrows(CustomAuthException.class,
                () -> {
                    service.deleteUser(userRoles.getUserId() - 1);
                });
        assertTrue(ex.getMessage().contains(AuthExceptionMessages.USER_DOES_NOT_EXIST));

        verify(repo, times(0)).deleteById(userRoles.getUserId() - 1);
    }
}
