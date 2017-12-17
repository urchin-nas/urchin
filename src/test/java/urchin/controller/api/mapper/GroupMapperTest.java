package urchin.controller.api.mapper;

import org.junit.Test;
import urchin.controller.api.group.GroupResponse;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static urchin.controller.api.mapper.GroupMapper.mapToGroupsResponses;

public class GroupMapperTest {

    @Test
    public void mappedToResponse() {
        Group group = ImmutableGroup.builder()
                .groupId(GroupId.of(1))
                .name(GroupName.of("groupName"))
                .created(LocalDateTime.now())
                .build();

        List<GroupResponse> groupResponses = mapToGroupsResponses(Collections.singletonList(group));

        assertEquals(1, groupResponses.size());
        GroupResponse groupResponse = groupResponses.get(0);
        assertEquals(group.getGroupId().getValue(), groupResponse.getGroupId());
        assertEquals(group.getName().getValue(), groupResponse.getGroupName());
    }

}