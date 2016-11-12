package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.GroupRepository;
import urchin.domain.cli.GroupCli;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    private static final GroupId GROUP_ID = new GroupId(1);

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupCli groupCli;

    @InjectMocks
    private GroupService groupService;
    private Group group;

    @Before
    public void setup() {
        group = new Group("groupname");
    }

    @Test
    public void addGroupIsSavedInGroupRepositoryAndCommandForAddingGroupIsCalled() {
        groupService.addGroup(group);

        verify(groupRepository).saveGroup(group);
        verify(groupCli).addGroup(group);
    }

    @Test
    public void removeGroupRemovesGroupFromGroupRepositoryAndRemoveGroupCommandIsCalled() {
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(Optional.of(group));

        groupService.removeGroup(GROUP_ID);

        verify(groupRepository).removeGroup(GROUP_ID);
        verify(groupCli).removeGroup(group);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeGroupThatDoesNotExistThrowsException() {
        when(groupRepository.getGroup(GROUP_ID)).thenReturn(Optional.empty());

        groupService.removeGroup(GROUP_ID);
    }

}