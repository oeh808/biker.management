package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.repo.BikerRepo;

@ActiveProfiles("test")
@DataMongoTest
public class BikerRepoTest {
    @Autowired
    private BikerRepo repo;

    private static Biker biker;

    @BeforeAll
    public static void setUp() {
        biker = new Biker(1, "Timmy", "Timmyyy@gmail.com", "+1512 3514000", "password", null);
    }

    @BeforeEach
    public void setUpForEach() {
        biker = repo.save(biker);
    }

    @AfterEach
    public void tearDownForEach() {
        repo.deleteAll();
    }

    @Test
    public void findByEmail_Existant() {
        Optional<Biker> opBiker = repo.findByEmail(biker.getEmail());

        assertTrue(opBiker.isPresent());
        assertEquals(biker, opBiker.get());
    }

    @Test
    public void findByEmail_NonExistant() {
        Optional<Biker> opBiker = repo.findByEmail("Blah");

        assertTrue(opBiker.isEmpty());
    }

    @Test
    public void findByPhoneNumber_Existant() {
        Optional<Biker> opBiker = repo.findByPhoneNumber(biker.getPhoneNumber());

        assertTrue(opBiker.isPresent());
        assertEquals(biker, opBiker.get());
    }

    @Test
    public void findByPhoneNumber_NonExistant() {
        Optional<Biker> opBiker = repo.findByPhoneNumber("Blah");

        assertTrue(opBiker.isEmpty());
    }
}
