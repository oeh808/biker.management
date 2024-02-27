package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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

@ActiveProfiles("test")
@DataJpaTest
public class UserInfoRepoTest {
    @Autowired
    private UserInfoRepo repo;

    private static UserInfo user1;
    private static UserInfo user2;

    @BeforeAll
    public static void setUp() {
        user1 = new UserInfo(50, "Durge", "123456", "ADMIN");
        user2 = new UserInfo(51, "Tav", "123456", "BACK_OFFICE");
    }

    @BeforeEach
    public void setUpForEach() {
        repo.save(user1);
        repo.save(user2);
    }

    @AfterEach
    public void tearDownForEach() {
        repo.deleteAll();
    }

    @Test
    public void findByUsername_Existant() {
        Optional<UserInfo> opUser = repo.findByUsername(user1.getUsername());

        assertTrue(opUser.isPresent());
        assertEquals(opUser.get(), user1);
    }

    @Test
    public void findByUsername_NonExistant() {
        Optional<UserInfo> opUser = repo.findByUsername(user1.getUsername() + "_");

        assertTrue(opUser.isEmpty());
    }

    @Test
    public void deleteByUsername_Existant() {
        assertTrue(repo.findAll().size() == 2);

        repo.deleteByUsername(user1.getUsername());
        List<UserInfo> users = repo.findAll();

        assertTrue(users.contains(user2));
        assertFalse(users.contains(user1));
    }

}
