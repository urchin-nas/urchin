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
@RequestMapping("api/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupResponse> getGroups() {
        List<Group> groups = groupService.getGroups();
        return mapToGroupsResponses(groups);
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GroupResponse getGroup(@PathVariable int groupId) {
        return mapToGroupResponse(groupService.getGroup(GroupId.of(groupId)));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public IdResponse addGroup(@Valid @RequestBody AddGroupRequest addGroupRequest) {
        GroupId groupId = groupService.addGroup(GroupName.of(addGroupRequest.getGroupName()));
        return ImmutableIdResponse.of(groupId.getValue());
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse removeGroup(@PathVariable int groupId) {
        groupService.removeGroup(GroupId.of(groupId));
        return ImmutableMessageResponse.of("Group removed");
    }

    @RequestMapping(value = "user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse addUserToGroup(@Valid @RequestBody AddUserToGroupRequest addUserToGroupRequest) {
        groupService.addUserToGroup(
                UserId.of(addUserToGroupRequest.getUserId()),
                GroupId.of(addUserToGroupRequest.getGroupId())
        );
        return ImmutableMessageResponse.of("User added to group");
    }

    @RequestMapping(value = "{groupId}/user/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse removeUserFromGroup(@PathVariable int groupId, @PathVariable int userId) {
        groupService.removeUserFromGroup(UserId.of(userId), GroupId.of(groupId));
        return ImmutableMessageResponse.of("User removed from group");
    }

    @RequestMapping(value = "{groupId}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserResponse> getGroupsForUser(@PathVariable int groupId) {
        return mapToUsersResponses(groupService.listUsersForGroup(GroupId.of(groupId)));
    }

}
