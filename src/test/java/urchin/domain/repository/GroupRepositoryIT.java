package urchin.domain.repository;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.exception.GroupNotFoundException;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.testutil.TestApplication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class GroupRepositoryIT extends TestApplication {

    private GroupRepository groupRepository;

    @Before
    public void setup() {
        groupRepository = new GroupRepository(namedParameterJdbcTemplate);
    }

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();
        Group group = new Group("groupname");

        GroupId groupId = groupRepository.saveGroup(group);
        Group readGroup = groupRepository.getGroup(groupId);

        assertEquals(group.getName(), readGroup.getName());
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
        groupRepository.saveGroup(new Group("groupname_1"));
        groupRepository.saveGroup(new Group("groupname_2"));
        groupRepository.saveGroup(new Group("groupname_3"));

        List<Group> groupsByName = groupRepository.getGroupsByName(Arrays.asList("groupname_1", "groupname_3"));

        List<String> groupNames = groupsByName.stream()
                .map(Group::getName)
                .collect(Collectors.toList());
        assertTrue(groupNames.contains("groupname_1"));
        assertTrue(groupNames.contains("groupname_3"));
        assertFalse(groupNames.contains("groupname_2"));
    }
}