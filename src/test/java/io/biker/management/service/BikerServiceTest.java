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

import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.exception.BikerException;
import io.biker.management.biker.exception.BikerExceptionMessages;
import io.biker.management.biker.repo.BikerRepo;
import io.biker.management.biker.service.BikerService;
import io.biker.management.biker.service.BikerServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BikerServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        BikerService service(BikerRepo repo, PasswordEncoder encoder) {
            return new BikerServiceImpl(repo, encoder);
        }
    }

    @MockBean
    private BikerRepo repo;

    @MockBean
    private PasswordEncoder encoder;

    @Autowired
    private BikerService service;

    private static Biker biker;

    @BeforeAll
    public static void setUp() {
        biker = new Biker(1, "Timmy", "Timmyyy@gmail.com", "+1512 3514000", "password", null);
    }

    @Test
    public void createBiker() {
        when(repo.save(biker)).thenReturn(biker);

        assertEquals(biker, service.createBiker(biker));
    }

    @Test
    public void getAllBikers() {
        List<Biker> bikers = new ArrayList<Biker>();
        bikers.add(biker);
        when(repo.findAll()).thenReturn(bikers);

        assertTrue(service.getAllBikers().contains(biker));
    }

    @Test
    public void getSingleBiker_Existant() {
        when(repo.findById(biker.getId())).thenReturn(Optional.of(biker));

        assertEquals(biker, service.getSingleBiker(biker.getId()));
    }

    @Test
    public void getSingleBiker_NonExistant() {
        when(repo.findById(biker.getId() - 1)).thenReturn(Optional.empty());

        BikerException ex = assertThrows(BikerException.class,
                () -> {
                    service.getSingleBiker(biker.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(BikerExceptionMessages.BIKER_NOT_FOUND));
    }

    @Test
    public void deleteBiker_Existant() {
        when(repo.findById(biker.getId())).thenReturn(Optional.of(biker));

        service.deleteBiker(biker.getId());

        verify(repo, times(1)).deleteById(biker.getId());
    }

    @Test
    public void deleteBiker_NonExistant() {
        when(repo.findById(biker.getId() - 1)).thenReturn(Optional.empty());

        BikerException ex = assertThrows(BikerException.class,
                () -> {
                    service.deleteBiker(biker.getId() - 1);
                });
        assertTrue(ex.getMessage().contains(BikerExceptionMessages.BIKER_NOT_FOUND));

        verify(repo, times(0)).deleteById(biker.getId() - 1);
    }
}
