package urchin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urchin.api.AddGroupApi;
import urchin.api.GroupsApi;
import urchin.api.support.ResponseMessage;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;
import urchin.service.GroupService;

import javax.validation.Valid;
import java.util.List;

import static urchin.api.mapper.GroupMapper.mapToGroup;
import static urchin.api.mapper.GroupMapper.mapToGroupsApi;
import static urchin.api.support.DataResponseEntityBuilder.createOkResponse;
import static urchin.api.support.DataResponseEntityBuilder.createResponse;

@RestController
@RequestMapping("groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<GroupsApi>> getGroups() {
        List<Group> groups = groupService.getGroups();
        return createResponse(mapToGroupsApi(groups));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> addGroup(@Valid @RequestBody AddGroupApi addGroupApi) {
        groupService.addGroup(mapToGroup(addGroupApi));
        return createOkResponse();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> removeGroup(@PathVariable int id) {
        groupService.removeGroup(new GroupId(id));
        return createOkResponse();
    }

}
