package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.repo.BikerRepo;

@ActiveProfiles("test")
@DataJpaTest
public class BikerRepoTest {
    @Autowired
    private BikerRepo repo;

    private static Biker biker1;
    private static Biker biker2;

    @BeforeAll
    public static void setUp() {
        biker1 = new Biker(50, "Durge", "Bhaal@gmail.com", "+666 9772223918", null);
        biker2 = new Biker(51, "Tav", "Unknown@gmail.com", "+333 9772223918", null);
    }

    @BeforeEach
    public void setUpForEach() {
        repo.save(biker1);
        repo.save(biker2);
    }

    @AfterEach
    public void tearDownForEach() {
        repo.deleteAll();
    }

    @Test
    public void findByPhoneNumber_Existant() {
        Optional<Biker> opBiker = repo.findByPhoneNumber(biker1.getPhoneNumber());

        assertTrue(opBiker.isPresent());
        assertEquals(opBiker.get().getName(), biker1.getName());
    }

    @Test
    public void findByPhoneNumber_NonExistant() {
        Optional<Biker> opBiker = repo.findByPhoneNumber(biker1.getPhoneNumber() + "_");

        assertTrue(opBiker.isEmpty());
    }

    @Test
    public void findByEmail_Existant() {
        Optional<Biker> opBiker = repo.findByEmail(biker1.getEmail());

        assertTrue(opBiker.isPresent());
        assertEquals(opBiker.get().getName(), biker1.getName());
    }

    @Test
    public void findByEmail_NonExistant() {
        Optional<Biker> opBiker = repo.findByEmail(biker1.getEmail() + "_");

        assertTrue(opBiker.isEmpty());
    }
}
