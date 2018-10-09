package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.IdResponse;
import urchin.controller.api.ImmutableIdResponse;
import urchin.controller.api.ImmutableMessageResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.group.AddGroupRequest;
import urchin.controller.api.group.AddUserToGroupRequest;
import urchin.controller.api.group.GroupResponse;
import urchin.controller.api.user.UserResponse;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.user.UserId;
import urchin.service.GroupService;

import javax.validation.Valid;
import java.util.List;

import static urchin.controller.mapper.GroupMapper.mapToGroupResponse;
import static urchin.controller.mapper.GroupMapper.mapToGroupsResponses;
import static urchin.controller.mapper.UserMapper.mapToUsersResponses;

@RestController
@RequestMapping(value = "api/groups", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupResponse> getGroups() {
        List<Group> groups = groupService.getGroups();
        return mapToGroupsResponses(groups);
    }

    @GetMapping(value = "{groupId}")
    public GroupResponse getGroup(@PathVariable int groupId) {
        return mapToGroupResponse(groupService.getGroup(GroupId.of(groupId)));
    }

    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public IdResponse addGroup(@Valid @RequestBody AddGroupRequest addGroupRequest) {
        GroupId groupId = groupService.addGroup(GroupName.of(addGroupRequest.getGroupName()));
        return ImmutableIdResponse.of(groupId.getValue());
    }

    @DeleteMapping(value = "{groupId}")
    public MessageResponse removeGroup(@PathVariable int groupId) {
        groupService.removeGroup(GroupId.of(groupId));
        return ImmutableMessageResponse.of("Group removed");
    }

    @PostMapping(value = "user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse addUserToGroup(@Valid @RequestBody AddUserToGroupRequest addUserToGroupRequest) {
        groupService.addUserToGroup(
                UserId.of(addUserToGroupRequest.getUserId()),
                GroupId.of(addUserToGroupRequest.getGroupId())
        );
        return ImmutableMessageResponse.of("User added to group");
    }

    @DeleteMapping(value = "{groupId}/user/{userId}")
    public MessageResponse removeUserFromGroup(@PathVariable int groupId, @PathVariable int userId) {
        groupService.removeUserFromGroup(UserId.of(userId), GroupId.of(groupId));
        return ImmutableMessageResponse.of("User removed from group");
    }

    @GetMapping(value = "{groupId}/users")
    public List<UserResponse> getGroupsForUser(@PathVariable int groupId) {
        return mapToUsersResponses(groupService.listUsersForGroup(GroupId.of(groupId)));
    }

}
