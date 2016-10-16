package urchin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.domain.UserRepository;
import urchin.domain.cli.user.AddUserCommand;
import urchin.domain.cli.user.RemoveUserCommand;
import urchin.domain.cli.user.SetUserPasswordCommand;
import urchin.domain.model.User;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AddUserCommand addUserCommand;
    private final SetUserPasswordCommand setUserPasswordCommand;
    private final RemoveUserCommand removeUserCommand;

    public UserService(UserRepository userRepository, AddUserCommand addUserCommand, SetUserPasswordCommand setUserPasswordCommand, RemoveUserCommand removeUserCommand) {
        this.userRepository = userRepository;
        this.addUserCommand = addUserCommand;
        this.setUserPasswordCommand = setUserPasswordCommand;
        this.removeUserCommand = removeUserCommand;
    }

    public void addUser(User user, String password) {
        userRepository.saveUser(user);
        addUserCommand.execute(user);
        setUserPasswordCommand.execute(user, password);
    }

    public void removeUser(int id) {
        Optional<User> userOptional = userRepository.getUser(id);
        if (userOptional.isPresent()) {
            userRepository.removeUser(id);
            removeUserCommand.execute(userOptional.get());
        }
    }
}
