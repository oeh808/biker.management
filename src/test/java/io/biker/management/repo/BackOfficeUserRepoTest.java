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

import io.biker.management.back_office.entity.BackOfficeUser;
import io.biker.management.back_office.repo.BackOfficeUserRepo;

@ActiveProfiles("test")
@DataJpaTest
public class BackOfficeUserRepoTest {
    @Autowired
    private BackOfficeUserRepo repo;

    private static BackOfficeUser boUser1;
    private static BackOfficeUser boUser2;

    @BeforeAll
    public static void setUp() {
        boUser1 = new BackOfficeUser(50, "Durge", "Bhaal@gmail.com", "+666 9772223918");
        boUser2 = new BackOfficeUser(51, "Tav", "Unknown@gmail.com", "+333 9772223918");
    }

    @BeforeEach
    public void setUpForEach() {
        repo.save(boUser1);
        repo.save(boUser2);
    }

    @AfterEach
    public void tearDownForEach() {
        repo.deleteAll();
    }

    @Test
    public void findByPhoneNumber_Existant() {
        Optional<BackOfficeUser> opBoUser = repo.findByPhoneNumber(boUser1.getPhoneNumber());

        assertTrue(opBoUser.isPresent());
        assertEquals(opBoUser.get().getName(), boUser1.getName());
    }

    @Test
    public void findByPhoneNumber_NonExistant() {
        Optional<BackOfficeUser> opBoUser = repo.findByPhoneNumber(boUser1.getPhoneNumber() + "_");

        assertTrue(opBoUser.isEmpty());
    }
}
