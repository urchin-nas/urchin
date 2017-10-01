package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.domain.cli.UserCli;
import urchin.domain.model.Group;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.domain.repository.GroupRepository;
import urchin.domain.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserCli userCli;

    @Autowired
    public UserService(
            UserRepository userRepository,
            GroupRepository groupRepository,
            UserCli userCli
    ) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userCli = userCli;
    }

    @Transactional
    public UserId addUser(String username, String password) {
        UserId userId = userRepository.saveUser(username);
        userCli.addUser(username);
        userCli.setSetUserPassword(username, password);
        return userId;
    }

    @Transactional
    public void removeUser(UserId userId) {
        User user = userRepository.getUser(userId);
        userRepository.removeUser(userId);
        userCli.removeUser(user.getUsername());
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUser(UserId userId) {
        return userRepository.getUser(userId);
    }

    public List<Group> listGroupsForUser(UserId userId) {
        User user = userRepository.getUser(userId);
        List<String> unixGroups = userCli.listGroupsForUser(user);
        return groupRepository.getGroupsByName(unixGroups);
    }
}
