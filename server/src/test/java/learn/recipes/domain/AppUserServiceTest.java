package learn.recipes.domain;

import learn.recipes.data.AppUserRepository;
import learn.recipes.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AppUserServiceTest {

    @Autowired
    private AppUserService appUserService;

    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testLoadUserByUsername() {
        String username = "testUser";
        AppUser mockAppUser = new AppUser(1, username, "encodedPassword", true, List.of("USER"));
        when(appUserRepository.findByUsername(username)).thenReturn(mockAppUser);

        AppUser loadedUser = appUserService.loadUserByUsername(username);

        assertEquals(mockAppUser, loadedUser);
    }

    @Test
    public void testLoadUserByUsernameWithInvalidUsername() {
        String username = "nonExistentUser";
        when(appUserRepository.findByUsername(username)).thenReturn(null);

        UsernameNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> appUserService.loadUserByUsername(username)
        );
        assertEquals(username + " not found.", exception.getMessage());
    }

    @Test
    public void testAddValidUser() {
        String username = "newUser";
        String password = "P@ssw0rd!";
        AppUser mockAppUser = new AppUser(1, username, "encodedPassword", true, List.of("USER"));
        when(appUserRepository.add(Mockito.any())).thenReturn(mockAppUser);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        Result<AppUser> result = appUserService.add(username, password);

        assertEquals(0, result.getMessages().size());
        assertEquals(mockAppUser, result.getPayload());
    }

    @Test
    public void testAddInvalidUser() {
        String username = "";
        String password = "";

        Result<AppUser> result = appUserService.add(username, password);

        assertEquals(2, result.getMessages().size());
    }
}
