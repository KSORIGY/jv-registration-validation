package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
    }

    @Test
    public void registerUser_AddUserToStorage_Ok() {
        User validUser = new User();
        validUser.setId(123L);
        validUser.setLogin("user1@gmail.com");
        validUser.setPassword("user1password");
        validUser.setAge(22);
        registrationService.register(validUser);
        assertEquals(1, Storage.people.size());
        assertEquals(validUser, Storage.people.get(0));
    }

    @Test
    public void registerUser_NullUser_throwsException_NotOk() {
        User nullUser = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    public void registerUser_ShortLogin_ThrowsException_NotOk() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void registerUser_ShortPassword_ThrowsException_NotOk() {
        User user = new User();
        user.setPassword("pass");
        user.setLogin("user1gmail.com");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void registerUser_WrongAge_ThrowsException_NotOk() {
        User user = new User();
        user.setAge(17);
        user.setLogin("user1gmail.com");
        user.setPassword("password");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void registerUser_NullLogin_ThrowsException_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void registerUser_NullPassword_ThrowsException_NotOk() {
        User user = new User();
        user.setPassword(null);
        user.setLogin("user1gmail.com");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void registerUser_NullAge_ThrowsException_NotOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("user1gmail.com");
        user.setPassword("password");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void registerUser_UserAlreadyExists_ThrowsException_NotOk() {
        User user = new User();
        user.setLogin("user1gmail.com");
        user.setPassword("user1password");
        user.setAge(22);
        registrationService.register(user);

        User user2 = new User();
        user2.setLogin("user1gmail.com");
        user2.setPassword("anotherPassword");
        user2.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }
}
