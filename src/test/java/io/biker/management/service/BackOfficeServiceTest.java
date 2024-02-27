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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.biker.management.back_office.entity.BackOfficeUser;
import io.biker.management.back_office.exception.BackOfficeException;
import io.biker.management.back_office.exception.BackOfficeExceptionMessages;
import io.biker.management.back_office.repo.BackOfficeUserRepo;
import io.biker.management.back_office.service.BackOfficeService;
import io.biker.management.back_office.service.BackOfficeServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BackOfficeServiceTest {

    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        BackOfficeService service(BackOfficeUserRepo repo) {
            return new BackOfficeServiceImpl(repo);
        }
    }

    @MockBean
    private BackOfficeUserRepo repo;

    @Autowired
    private BackOfficeService service;

    private static BackOfficeUser boUser;

    @BeforeAll
    public static void setUp() {
        boUser = new BackOfficeUser(1, "Gale", "MagicMan@gmail.com", "+44 770820695");
    }

    @Test
    public void createBackOfficeUser() {
        when(repo.save(boUser)).thenReturn(boUser);

        assertEquals(boUser, service.createBackOfficeUser(boUser));
    }

    @Test
    public void getAllBackOfficeUsers() {
        List<BackOfficeUser> boUsers = new ArrayList<BackOfficeUser>();
        boUsers.add(boUser);
        when(repo.findAll()).thenReturn(boUsers);

        assertTrue(service.getAllBackOfficeUsers().contains(boUser));
    }

    @Test
    public void getSingleBackOfficeUser_Existant() {
        when(repo.findById(boUser.getId())).thenReturn(Optional.of(boUser));

        assertEquals(boUser, service.getSingleBackOfficeUser(boUser.getId()));
    }

    @Test
    public void getSingleBackOfficeUser_NonExistant() {
        when(repo.findById(boUser.getId() - 1)).thenReturn(Optional.empty());

        BackOfficeException ex = assertThrows(BackOfficeException.class,
                () -> {
                    service.getSingleBackOfficeUser(boUser.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(BackOfficeExceptionMessages.BACK_OFFICE_USER_NOT_FOUND));
    }

    @Test
    public void deleteBackOfficeUser_Existant() {
        when(repo.findById(boUser.getId())).thenReturn(Optional.of(boUser));

        service.deleteBackOfficeUser(boUser.getId());

        verify(repo, times(1)).deleteById(boUser.getId());
    }

    @Test
    public void deleteBackOfficeUser_NonExistant() {
        when(repo.findById(boUser.getId() - 1)).thenReturn(Optional.empty());

        BackOfficeException ex = assertThrows(BackOfficeException.class,
                () -> {
                    service.getSingleBackOfficeUser(boUser.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(BackOfficeExceptionMessages.BACK_OFFICE_USER_NOT_FOUND));

        verify(repo, times(0)).deleteById(boUser.getId() - 1);
    }
}