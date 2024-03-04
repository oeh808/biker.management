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

import io.biker.management.auth.entity.UserInfo;
import io.biker.management.auth.repo.UserInfoRepo;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.repo.BackOfficeUserRepo;

@ActiveProfiles("test")
@DataJpaTest
public class UserInfoRepoTest {
    @Autowired
    private BackOfficeUserRepo backOfficeUserRepo;

    @Autowired
    private UserInfoRepo userInfoRepo;

    private static BackOfficeUser backOfficeUser1;
    private static BackOfficeUser backOfficeUser2;

    private UserInfo user1;
    private UserInfo user2;

    @BeforeAll
    public static void setUp() {
        backOfficeUser1 = new BackOfficeUser(50, "Durge", "Bhaal@gmail.com", "+666 9772223918", "password");
        backOfficeUser2 = new BackOfficeUser(51, "Tav", "Vanilla@gmail.com", "+333 9772223918", "password");
    }

    @BeforeEach
    public void setUpForEach() {
        backOfficeUser1 = backOfficeUserRepo.save(backOfficeUser1);
        backOfficeUser2 = backOfficeUserRepo.save(backOfficeUser2);

        user1 = new UserInfo(backOfficeUser1.getId(), backOfficeUser1,
                "BACK_OFFICE");
        user2 = new UserInfo(backOfficeUser2.getId(), backOfficeUser2,
                "BACK_OFFICE");

        user1 = userInfoRepo.save(user1);
        user2 = userInfoRepo.save(user2);
    }

    @AfterEach
    public void tearDownForEach() {
        userInfoRepo.deleteAll();
        backOfficeUserRepo.deleteAll();
    }

    @Test
    public void findByUser_Email_Existant() {
        Optional<UserInfo> opUser = userInfoRepo.findByUser_Email(user1.getUser().getEmail());

        assertTrue(opUser.isPresent());
        assertEquals(opUser.get(), user1);
    }

    @Test
    public void findByUser_Email_NonExistant() {
        Optional<UserInfo> opUser = userInfoRepo.findByUser_Email(user1.getUser().getEmail() + "_");

        assertTrue(opUser.isEmpty());
    }

    @Test
    public void findByUser_PhoneNumber_Existant() {
        Optional<UserInfo> opUser = userInfoRepo.findByUser_PhoneNumber(user1.getUser().getPhoneNumber());

        assertTrue(opUser.isPresent());
        assertEquals(opUser.get(), user1);
    }

    @Test
    public void findByUser_PhoneNumber_NonExistant() {
        Optional<UserInfo> opUser = userInfoRepo.findByUser_PhoneNumber(user1.getUser().getPhoneNumber() +
                "_");

        assertTrue(opUser.isEmpty());
    }
}
