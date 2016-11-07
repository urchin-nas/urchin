package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.cli.group.CheckIfGroupExistCommand;
import urchin.domain.GroupRepository;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.testutil.TestApplication;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static urchin.testutil.OsAssumption.ignoreWhenWindowsOrMac;

public class GroupServiceIT extends TestApplication {

    private static final String GROUP_PREFIX = "urchin_";

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CheckIfGroupExistCommand checkIfGroupExistCommand;

    private Group group;

    @Before
    public void setup() {
        ignoreWhenWindowsOrMac();
    }

    @Test
    public void GroupIsCreatedAndRemoved() {
        group = new Group(GROUP_PREFIX + System.currentTimeMillis());

        GroupId groupId = groupService.addGroup(group);

        assertTrue(groupRepository.getGroup(groupId).isPresent());
        assertTrue(checkIfGroupExistCommand.execute(group.getName()));

        groupService.removeGroup(groupId);

        assertFalse(groupRepository.getGroup(groupId).isPresent());
        assertFalse(checkIfGroupExistCommand.execute(group.getName()));
    }

    @Test
    public void addingUserWithoutUsernameShouldFail() {
        group = new Group("");

        try {
            groupService.addGroup(group);
            fail("expected addGroup to throw exception");
        } catch (Exception e) {
            List<Group> matchingGroups = groupService.getGroups().stream()
                    .filter(foundGroup -> foundGroup.getName().equals(group.getName()))
                    .collect(Collectors.toList());

            assertEquals(0, matchingGroups.size());
            assertFalse(checkIfGroupExistCommand.execute(group.getName()));
        }

    }

}