package urchin.controller.mapper;

import org.junit.Test;
import urchin.controller.api.group.GroupResponse;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static urchin.controller.mapper.GroupMapper.mapToGroupsResponses;

public class GroupMapperTest {

    @Test
    public void mappedToResponse() {
        Group group = ImmutableGroup.builder()
                .groupId(GroupId.of(1))
                .name(GroupName.of("groupName"))
                .created(LocalDateTime.now())
                .build();

        List<GroupResponse> groupResponses = mapToGroupsResponses(Collections.singletonList(group));

        assertThat(groupResponses).hasSize(1);
        GroupResponse groupResponse = groupResponses.get(0);
        assertThat(groupResponse.getGroupId()).isEqualTo(group.getGroupId().getValue());
        assertThat(groupResponse.getGroupName()).isEqualTo(group.getName().getValue());
    }

}