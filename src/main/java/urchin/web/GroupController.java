package urchin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urchin.api.AddGroupDto;
import urchin.api.GroupsDto;
import urchin.api.support.ResponseMessage;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.service.GroupService;

import javax.validation.Valid;
import java.util.List;

import static urchin.api.mapper.GroupMapper.mapToGroup;
import static urchin.api.mapper.GroupMapper.mapToGroupsDto;
import static urchin.api.support.DataResponseEntityBuilder.createOkResponse;
import static urchin.api.support.DataResponseEntityBuilder.createResponse;

@RestController
@RequestMapping("api/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<GroupsDto>> getGroups() {
        List<Group> groups = groupService.getGroups();
        return createResponse(mapToGroupsDto(groups));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<Integer>> addGroup(@Valid @RequestBody AddGroupDto addGroupDto) {
        GroupId groupId = groupService.addGroup(mapToGroup(addGroupDto));
        return createResponse(groupId.getId());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> removeGroup(@PathVariable int id) {
        groupService.removeGroup(new GroupId(id));
        return createOkResponse();
    }

}
