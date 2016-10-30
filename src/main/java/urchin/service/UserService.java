package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.cli.user.AddUserCommand;
import urchin.cli.user.RemoveUserCommand;
import urchin.cli.user.SetUserPasswordCommand;
import urchin.domain.UserRepository;
import urchin.domain.model.User;
import urchin.domain.model.UserId;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddUserCommand addUserCommand;
    private final SetUserPasswordCommand setUserPasswordCommand;
    private final RemoveUserCommand removeUserCommand;

    @Autowired
    public UserService(UserRepository userRepository, AddUserCommand addUserCommand, SetUserPasswordCommand setUserPasswordCommand, RemoveUserCommand removeUserCommand) {
        this.userRepository = userRepository;
        this.addUserCommand = addUserCommand;
        this.setUserPasswordCommand = setUserPasswordCommand;
        this.removeUserCommand = removeUserCommand;
    }

    @Transactional
    public UserId addUser(User user, String password) {
        UserId userId = userRepository.saveUser(user);
        addUserCommand.execute(user);
        setUserPasswordCommand.execute(user, password);
        return userId;
    }

    @Transactional
    public void removeUser(UserId userId) {
        Optional<User> userOptional = userRepository.getUser(userId);
        if (userOptional.isPresent()) {
            userRepository.removeUser(userId);
            removeUserCommand.execute(userOptional.get());
        } else {
            throw new IllegalArgumentException("Invalid userId: " + userId);
        }
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }
}
