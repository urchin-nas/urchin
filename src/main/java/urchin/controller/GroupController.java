package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.*;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.domain.model.UserId;
import urchin.service.GroupService;

import javax.validation.Valid;
import java.util.List;

import static urchin.controller.api.mapper.GroupMapper.mapToGroup;
import static urchin.controller.api.mapper.GroupMapper.mapToGroupDto;
import static urchin.controller.api.mapper.GroupMapper.mapToGroupsDto;

@RestController
@RequestMapping("api/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupDto> getGroups() {
        List<Group> groups = groupService.getGroups();
        return mapToGroupsDto(groups);
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDto getGroup(@PathVariable int groupId) {
        GroupId gid = new GroupId(groupId);
        return mapToGroupDto(groupService.getGroup(gid)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid GroupId: %s", gid)))
        );
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public IdDto addGroup(@Valid @RequestBody AddGroupDto addGroupDto) {
        GroupId groupId = groupService.addGroup(mapToGroup(addGroupDto));
        return new IdDto(groupId.getId());
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto removeGroup(@PathVariable int groupId) {
        groupService.removeGroup(new GroupId(groupId));
        return new MessageDto("Group removed");
    }

    @RequestMapping(value = "user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto addUserToGroup(@Valid @RequestBody AddUserToGroupDto addUserToGroupDto) {
        groupService.addUserToGroup(new UserId(addUserToGroupDto.getUserId()), new GroupId(addUserToGroupDto.getGroupId()));
        return new MessageDto("User added to group");
    }

    @RequestMapping(value = "{groupId}/user/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto removeUserFromGroup(@PathVariable int groupId, @PathVariable int userId) {
        groupService.removeUserFromGroup(new UserId(userId), new GroupId(groupId));
        return new MessageDto("User removed from group");
    }

}
