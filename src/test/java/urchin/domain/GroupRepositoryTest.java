package urchin.domain;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.testutil.H2Application;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class GroupRepositoryTest extends H2Application {

    private GroupRepository groupRepository;

    @Before
    public void setup() {
        groupRepository = new GroupRepository(jdbcTemplate);
    }

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();
        Group group = new Group("groupname");

        GroupId groupId = groupRepository.saveGroup(group);
        Optional<Group> groupOptional = groupRepository.getGroup(groupId);

        assertTrue(groupOptional.isPresent());
        Group readGroup = groupOptional.get();
        assertEquals(group.getName(), readGroup.getName());
        assertTrue(now.isBefore(readGroup.getCreated()));

        groupRepository.removeGroup(groupId);

        assertFalse(groupRepository.getGroup(groupId).isPresent());
    }
}