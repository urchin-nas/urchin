package urchin.domain;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.model.User;
import urchin.testutil.H2Application;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserRepositoryTest extends H2Application {

    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository = new UserRepository(jdbcTemplate);
    }

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User("username");

        int id = userRepository.saveUser(user);
        Optional<User> userOptional = userRepository.getUser(id);

        assertTrue(userOptional.isPresent());
        User readUser = userOptional.get();
        assertEquals(user.getUsername(), readUser.getUsername());
        assertTrue(now.isBefore(readUser.getCreated()));

        userRepository.removeUser(id);

        assertFalse(userRepository.getUser(id).isPresent());
    }
}