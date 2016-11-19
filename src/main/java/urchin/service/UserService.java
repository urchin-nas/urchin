package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.domain.UserRepository;
import urchin.domain.cli.UserCli;
import urchin.domain.model.User;
import urchin.domain.model.UserId;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserCli userCli;

    @Autowired
    public UserService(UserRepository userRepository, UserCli userCli) {
        this.userRepository = userRepository;
        this.userCli = userCli;
    }

    @Transactional
    public UserId addUser(User user, String password) {
        UserId userId = userRepository.saveUser(user);
        userCli.addUser(user);
        userCli.setSetUserPassword(user, password);
        return userId;
    }

    @Transactional
    public void removeUser(UserId userId) {
        Optional<User> userOptional = userRepository.getUser(userId);
        if (userOptional.isPresent()) {
            userRepository.removeUser(userId);
            userCli.removeUser(userOptional.get());
        } else {
            throw new IllegalArgumentException("Invalid userId: " + userId);
        }
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public Optional<User> getUser(UserId userId) {
        return userRepository.getUser(userId);
    }
}
