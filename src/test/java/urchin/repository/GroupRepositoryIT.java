package urchin.repository;

import org.junit.Before;
import org.junit.Test;
import urchin.exception.GroupNotFoundException;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.testutil.TestApplication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class GroupRepositoryIT extends TestApplication {

    private GroupRepository groupRepository;

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

        assertThat(readGroup.getName()).isEqualTo(groupName);
        assertThat(now.isAfter(readGroup.getCreated())).isFalse();

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
        assertThat(groupNames.contains(groupname_1)).isTrue();
        assertThat(groupNames.contains(groupname_3)).isTrue();
        assertThat(groupNames.contains(groupname_2)).isFalse();
    }
}