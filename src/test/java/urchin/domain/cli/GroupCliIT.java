package urchin.domain.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.RuntimeConfiguration;
import urchin.domain.cli.group.AddGroupCommand;
import urchin.domain.cli.group.CheckIfGroupExistCommand;
import urchin.domain.cli.group.RemoveGroupCommand;
import urchin.domain.model.Group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        RuntimeConfiguration.class,
        GroupCli.class,
        AddGroupCommand.class,
        CheckIfGroupExistCommand.class,
        RemoveGroupCommand.class
})
public class GroupCliIT {

    private static final String GROUP_PREFIX = "urchin_";

    @Autowired
    private GroupCli groupCli;

    @Test
    public void addGroupAndCheckIfGroupExistAndRemoveGroupAreExecutedSuccessfully() {
        Group group = new Group(GROUP_PREFIX + System.currentTimeMillis());

        groupCli.addGroup(group);

        assertTrue(groupCli.checkIfGroupExist(group.getName()));

        groupCli.removeGroup(group);

        assertFalse(groupCli.checkIfGroupExist(group.getName()));
    }

}