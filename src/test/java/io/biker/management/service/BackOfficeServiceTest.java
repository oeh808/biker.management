package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.exception.BackOfficeException;
import io.biker.management.backOffice.exception.BackOfficeExceptionMessages;
import io.biker.management.backOffice.repo.BackOfficeUserRepo;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.backOffice.service.BackOfficeServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BackOfficeServiceTest {

    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        BackOfficeService service(BackOfficeUserRepo repo, PasswordEncoder encoder) {
            return new BackOfficeServiceImpl(repo, encoder);
        }
    }

    @MockBean
    private BackOfficeUserRepo repo;

    @MockBean
    private PasswordEncoder encoder;

    @Autowired
    private BackOfficeService service;

    private static BackOfficeUser backOfficeUser;

    @BeforeAll
    public static void setUp() {
        backOfficeUser = new BackOfficeUser(1, "Gale", "MagicMan@gmail.com", "+44 770820695", "password");
    }

    @Test
    public void createBackOfficeUser_Success() {
        when(repo.save(backOfficeUser)).thenReturn(backOfficeUser);

        assertEquals(backOfficeUser, service.createBackOfficeUser(backOfficeUser));
    }

    @Test
    public void createBackOfficeUser_DuplicateInfo() {
        when(repo.findByEmail(backOfficeUser.getEmail())).thenReturn(Optional.of(backOfficeUser));
        when(repo.findByPhoneNumber(backOfficeUser.getPhoneNumber())).thenReturn(Optional.of(backOfficeUser));

        CustomAuthException ex = assertThrows(CustomAuthException.class,
                () -> {
                    service.createBackOfficeUser(backOfficeUser);
                });
        assertTrue(ex.getMessage().contains(AuthExceptionMessages.DUPLICATE_DATA));
    }

    @Test
    public void getAllBackOfficeUsers() {
        List<BackOfficeUser> backOfficeUsers = new ArrayList<BackOfficeUser>();
        backOfficeUsers.add(backOfficeUser);
        when(repo.findAll()).thenReturn(backOfficeUsers);

        assertTrue(service.getAllBackOfficeUsers().contains(backOfficeUser));
    }

    @Test
    public void getSingleBackOfficeUser_Existant() {
        when(repo.findById(backOfficeUser.getId())).thenReturn(Optional.of(backOfficeUser));

        assertEquals(backOfficeUser, service.getSingleBackOfficeUser(backOfficeUser.getId()));
    }

    @Test
    public void getSingleBackOfficeUser_NonExistant() {
        when(repo.findById(backOfficeUser.getId() - 1)).thenReturn(Optional.empty());

        BackOfficeException ex = assertThrows(BackOfficeException.class,
                () -> {
                    service.getSingleBackOfficeUser(backOfficeUser.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(BackOfficeExceptionMessages.BACK_OFFICE_USER_NOT_FOUND));
    }

    @Test
    public void deleteBackOfficeUser_Existant() {
        when(repo.findById(backOfficeUser.getId())).thenReturn(Optional.of(backOfficeUser));

        service.deleteBackOfficeUser(backOfficeUser.getId());

        verify(repo, times(1)).deleteById(backOfficeUser.getId());
    }

    @Test
    public void deleteBackOfficeUser_NonExistant() {
        when(repo.findById(backOfficeUser.getId() - 1)).thenReturn(Optional.empty());

        BackOfficeException ex = assertThrows(BackOfficeException.class,
                () -> {
                    service.getSingleBackOfficeUser(backOfficeUser.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(BackOfficeExceptionMessages.BACK_OFFICE_USER_NOT_FOUND));

        verify(repo, times(0)).deleteById(backOfficeUser.getId() - 1);
    }
}
