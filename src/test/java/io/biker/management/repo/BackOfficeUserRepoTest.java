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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.repo.BackOfficeUserRepo;

@ActiveProfiles("test")
@DataMongoTest
public class BackOfficeUserRepoTest {
    @Autowired
    private BackOfficeUserRepo repo;

    private static BackOfficeUser backOfficeUser;

    @BeforeAll
    public static void setUp() {
        backOfficeUser = new BackOfficeUser(50, "Durge", "Bhaal@gmail.com", "+666 9772223918", "password");
    }

    @BeforeEach
    public void setUpForEach() {
        backOfficeUser = repo.save(backOfficeUser);
    }

    @AfterEach
    public void tearDownForEach() {
        repo.deleteAll();
    }

    @Test
    public void findByEmail_Existant() {
        Optional<BackOfficeUser> opBackOfficeUser = repo.findByEmail(backOfficeUser.getEmail());

        assertTrue(opBackOfficeUser.isPresent());
        assertEquals(backOfficeUser, opBackOfficeUser.get());
    }

    @Test
    public void findByEmail_NonExistant() {
        Optional<BackOfficeUser> opBackOfficeUser = repo.findByEmail("Blah");

        assertTrue(opBackOfficeUser.isEmpty());
    }

    @Test
    public void findByPhoneNumber_Existant() {
        Optional<BackOfficeUser> opBackOfficeUser = repo.findByPhoneNumber(backOfficeUser.getPhoneNumber());

        assertTrue(opBackOfficeUser.isPresent());
        assertEquals(backOfficeUser, opBackOfficeUser.get());
    }

    @Test
    public void findByPhoneNumber_NonExistant() {
        Optional<BackOfficeUser> opBackOfficeUser = repo.findByPhoneNumber("Blah");

        assertTrue(opBackOfficeUser.isEmpty());
    }
}
