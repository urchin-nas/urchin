package urchin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.cli.UserCli;
import urchin.model.group.Group;
import urchin.model.group.GroupName;
import urchin.model.user.Password;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;
import urchin.repository.GroupRepository;
import urchin.repository.UserRepository;

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
    public UserId addUser(Username username, Password password) {
        UserId userId = userRepository.saveUser(username);
        userCli.addUser(username);
        userCli.setUserPassword(username, password);
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
        List<GroupName> unixGroups = userCli.listGroupsForUser(user);
        return groupRepository.getGroupsByName(unixGroups);
    }
}
