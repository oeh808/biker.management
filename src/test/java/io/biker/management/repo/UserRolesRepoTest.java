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

import io.biker.management.auth.entity.UserRoles;
import io.biker.management.auth.repo.UserRolesRepo;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.repo.BackOfficeUserRepo;

// FIXME: Ensure test is up to date
@ActiveProfiles("test")
@DataJpaTest
public class UserRolesRepoTest {
    @Autowired
    private BackOfficeUserRepo backOfficeUserRepo;

    @Autowired
    private UserRolesRepo userRolesRepo;

    private static BackOfficeUser backOfficeUser1;
    private static BackOfficeUser backOfficeUser2;

    private UserRoles userRoles1;
    private UserRoles userRoles2;

    @BeforeAll
    public static void setUp() {
        backOfficeUser1 = new BackOfficeUser(50, "Durge", "Bhaal@gmail.com", "+666 9772223918", "password");
        backOfficeUser2 = new BackOfficeUser(51, "Tav", "Vanilla@gmail.com", "+333 9772223918", "password");
    }

    @BeforeEach
    public void setUpForEach() {
        backOfficeUser1 = backOfficeUserRepo.save(backOfficeUser1);
        backOfficeUser2 = backOfficeUserRepo.save(backOfficeUser2);

        userRoles1 = new UserRoles(backOfficeUser1.getId(), backOfficeUser1,
                "BACK_OFFICE");
        userRoles2 = new UserRoles(backOfficeUser2.getId(), backOfficeUser2,
                "BACK_OFFICE");

        userRoles1 = userRolesRepo.save(userRoles1);
        userRoles2 = userRolesRepo.save(userRoles2);
    }

    @AfterEach
    public void tearDownForEach() {
        userRolesRepo.deleteAll();
        backOfficeUserRepo.deleteAll();
    }

    @Test
    public void findByUser_Email_Existant() {
        Optional<UserRoles> opUser = userRolesRepo.findByUser_Email(userRoles1.getUser().getEmail());

        assertTrue(opUser.isPresent());
        assertEquals(opUser.get(), userRoles1);
    }

    @Test
    public void findByUser_Email_NonExistant() {
        Optional<UserRoles> opUser = userRolesRepo.findByUser_Email(userRoles1.getUser().getEmail() + "_");

        assertTrue(opUser.isEmpty());
    }

    @Test
    public void findByUser_PhoneNumber_Existant() {
        Optional<UserRoles> opUser = userRolesRepo.findByUser_PhoneNumber(userRoles1.getUser().getPhoneNumber());

        assertTrue(opUser.isPresent());
        assertEquals(opUser.get(), userRoles1);
    }

    @Test
    public void findByUser_PhoneNumber_NonExistant() {
        Optional<UserRoles> opUser = userRolesRepo.findByUser_PhoneNumber(userRoles1.getUser().getPhoneNumber() +
                "_");

        assertTrue(opUser.isEmpty());
    }
}
