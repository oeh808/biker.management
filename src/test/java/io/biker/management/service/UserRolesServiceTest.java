package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.biker.management.admin.entity.Admin;
import io.biker.management.auth.entity.UserRoles;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserRolesRepo;
import io.biker.management.auth.service.UserRolesServiceImpl;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;
import io.biker.management.constants.Roles_Const;
import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.service.CustomerService;
import io.biker.management.product.entity.Product;
import io.biker.management.store.entity.Store;
import io.biker.management.store.service.StoreService;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserRolesServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        UserRolesServiceImpl service(UserRolesRepo repo, CustomerService customerService, BikerService bikerService,
                BackOfficeService backOfficeService, StoreService storeService) {
            return new UserRolesServiceImpl(repo, customerService, bikerService, backOfficeService, storeService);
        }
    }

    @MockBean
    private UserRolesRepo repo;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private BikerService bikerService;

    @MockBean
    private BackOfficeService backOfficeService;

    @MockBean
    private StoreService storeService;

    @Autowired
    private UserRolesServiceImpl userRolesService;

    private static Customer customer;
    private static Biker biker;
    private static BackOfficeUser backOfficeUser;
    private static Store store;

    private static UserRoles userRoles;
    private static List<String> roles;

    @BeforeAll
    public static void setUp() {
        customer = new Customer(50, "Volo", "BardMan", "+44 770820695", "password");
        biker = new Biker(1, "Timmy", "Timmyyy@gmail.com", "+1512 3514000", "password", null);
        backOfficeUser = new BackOfficeUser(1, "Gale", "MagicMan@gmail.com", "+44 770820695", "password");
        store = new Store(50, "Sorcerous Sundries", "SorcerousSundries@gmail.com", "+44 920350022",
                "password", null, new ArrayList<Product>());

        roles = new ArrayList<>();

        userRoles = new UserRoles(1, new Admin(1, "Durge", "Bhaal@gmail.com", "+666 9772223918", "password"),
                roles);
    }

    @BeforeEach
    public void setUpMocks() {
        roles = new ArrayList<>();
        userRoles.setRoles(roles);

        when(repo.save(userRoles)).thenReturn(userRoles);

        when(repo.findById(userRoles.getUserId())).thenReturn(Optional.of(userRoles));
        when(repo.findById(userRoles.getUserId() - 1)).thenReturn(Optional.empty());

        when(customerService.getSingleCustomer(customer.getId())).thenReturn(customer);
        when(bikerService.getSingleBiker(biker.getId())).thenReturn(biker);
        when(backOfficeService.getSingleBackOfficeUser(backOfficeUser.getId())).thenReturn(backOfficeUser);
        when(storeService.getSingleStore(store.getId())).thenReturn(store);
    }

    @Test
    public void addUser_Successful() {
        assertEquals(userRoles, userRolesService.addUser(userRoles));
    }

    @Test
    public void addUser_DuplicateData() {
        when(repo.findByUser_Email(userRoles.getUser().getEmail())).thenReturn(Optional.of(userRoles));
        when(repo.findByUser_PhoneNumber(userRoles.getUser().getPhoneNumber())).thenReturn(Optional.of(userRoles));

        CustomAuthException ex = assertThrows(CustomAuthException.class,
                () -> {
                    userRolesService.addUser(userRoles);
                });
        assertTrue(ex.getMessage().contains(AuthExceptionMessages.DUPLICATE_DATA));
    }

    @Test
    public void registerCustomer() {
        doAnswer((i) -> {
            return i.getArgument(0);
        }).when(repo).save(any(UserRoles.class));

        userRoles = userRolesService.registerCustomer(customer.getId());
        verify(customerService, times(1)).getSingleCustomer(customer.getId());

        assertEquals(Roles_Const.CUSTOMER, userRoles.getRoles().get(0));

        verify(repo, times(1)).save(userRoles);
    }

    @Test
    public void registerBiker() {
        doAnswer((i) -> {
            return i.getArgument(0);
        }).when(repo).save(any(UserRoles.class));

        userRoles = userRolesService.registerBiker(biker.getId());
        verify(bikerService, times(1)).getSingleBiker(biker.getId());

        assertEquals(Roles_Const.BIKER, userRoles.getRoles().get(0));

        verify(repo, times(1)).save(userRoles);
    }

    @Test
    public void registerBackOfficeUser() {
        doAnswer((i) -> {
            return i.getArgument(0);
        }).when(repo).save(any(UserRoles.class));

        userRoles = userRolesService.registerBackOfficeUser(backOfficeUser.getId());
        verify(backOfficeService, times(1)).getSingleBackOfficeUser(backOfficeUser.getId());

        assertEquals(Roles_Const.BACK_OFFICE, userRoles.getRoles().get(0));

        verify(repo, times(1)).save(userRoles);
    }

    @Test
    public void registerStore() {
        doAnswer((i) -> {
            return i.getArgument(0);
        }).when(repo).save(any(UserRoles.class));

        userRoles = userRolesService.registerStore(store.getId());
        verify(storeService, times(1)).getSingleStore(store.getId());

        assertEquals(Roles_Const.STORE, userRoles.getRoles().get(0));

        verify(repo, times(1)).save(userRoles);
    }

    @Test
    public void deleteUser_Existant() {
        userRolesService.deleteUser(userRoles.getUserId());

        verify(repo, times(1)).deleteById(userRoles.getUserId());
    }

    @Test
    public void deleteUser_NonExistant() {
        CustomAuthException ex = assertThrows(CustomAuthException.class,
                () -> {
                    userRolesService.deleteUser(userRoles.getUserId() - 1);
                });
        assertTrue(ex.getMessage().contains(AuthExceptionMessages.USER_DOES_NOT_EXIST));

        verify(repo, times(0)).deleteById(userRoles.getUserId() - 1);
    }
}
