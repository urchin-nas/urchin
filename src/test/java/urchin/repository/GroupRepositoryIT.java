package urchin.repository;

import org.junit.Before;
import org.junit.Test;
import urchin.exception.GroupNotFoundException;
import urchin.model.Group;
import urchin.model.GroupId;
import urchin.model.GroupName;
import urchin.testutil.TestApplication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class GroupRepositoryIT extends TestApplication {

    private GroupRepository groupRepository;
    ;

    @Before
    public void setup() {
        groupRepository = new GroupRepository(namedParameterJdbcTemplate);
    }

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();
        GroupName groupName = GroupName.of("groupName");

        GroupId groupId = groupRepository.saveGroup(groupName);
        Group readGroup = groupRepository.getGroup(groupId);

        assertEquals(groupName, readGroup.getName());
        assertFalse(now.isAfter(readGroup.getCreated()));

        groupRepository.removeGroup(groupId);

        try {
            groupRepository.getGroup(groupId);
            fail("expected exception");
        } catch (GroupNotFoundException ignore) {

        }
    }

    @Test
    public void getGroupsByNameReturnGroups() {
        GroupName groupname_1 = GroupName.of("groupname_1");
        GroupName groupname_2 = GroupName.of("groupname_2");
        GroupName groupname_3 = GroupName.of("groupname_3");
        groupRepository.saveGroup(groupname_1);
        groupRepository.saveGroup(groupname_2);
        groupRepository.saveGroup(groupname_3);

        List<Group> groupsByName = groupRepository.getGroupsByName(Arrays.asList(groupname_1, groupname_3));

        List<GroupName> groupNames = groupsByName.stream()
                .map(Group::getName)
                .collect(Collectors.toList());
        assertTrue(groupNames.contains(groupname_1));
        assertTrue(groupNames.contains(groupname_3));
        assertFalse(groupNames.contains(groupname_2));
    }
}