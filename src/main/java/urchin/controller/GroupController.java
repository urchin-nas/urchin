package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urchin.api.AddGroupDto;
import urchin.api.AddUserToGroupDto;
import urchin.api.GroupDto;
import urchin.api.support.ErrorResponse;
import urchin.api.support.ResponseMessage;
import urchin.api.support.error.ResponseException;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.domain.model.UserId;
import urchin.service.GroupService;

import javax.validation.Valid;
import java.util.List;

import static urchin.api.mapper.GroupMapper.mapToGroup;
import static urchin.api.mapper.GroupMapper.mapToGroupsDto;
import static urchin.api.support.ResponseEntityBuilder.*;

@RestController
@RequestMapping("api/groups")
public class GroupController {

    private static final String INVALID_GROUP_ID = "INVALID_GROUP_ID";

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<List<GroupDto>>> getGroups() {
        List<Group> groups = groupService.getGroups();
        return createResponse(mapToGroupsDto(groups));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<Integer>> addGroup(@Valid @RequestBody AddGroupDto addGroupDto) {
        GroupId groupId = groupService.addGroup(mapToGroup(addGroupDto));
        return createResponse(groupId.getId());
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> removeGroup(@PathVariable int groupId) {
        try {
            groupService.removeGroup(new GroupId(groupId));
        } catch (IllegalArgumentException e) {
            return createErrorResponse(new ResponseException(HttpStatus.BAD_REQUEST, new ErrorResponse(INVALID_GROUP_ID).setField("groupId").setMessage(e.getMessage()), e));
        }
        return createOkResponse();
    }

    @RequestMapping(value = "user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> addUserToGroup(@Valid @RequestBody AddUserToGroupDto addUserToGroupDto) {
        groupService.addUserToGroup(new UserId(addUserToGroupDto.getUserId()), new GroupId(addUserToGroupDto.getGroupId()));
        return createOkResponse();
    }

    @RequestMapping(value = "{groupId}/user/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> removeUserFromGroup(@PathVariable int groupId, @PathVariable int userId) {
        groupService.removeUserFromGroup(new UserId(userId), new GroupId(groupId));
        return createOkResponse();
    }

}
