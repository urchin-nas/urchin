package urchin.api;

import java.util.List;

public class GroupsApi {

    private List<GroupApi> groups;

    private GroupsApi() {
    }

    public GroupsApi(List<GroupApi> groupApis) {
        this.groups = groupApis;
    }

    public List<GroupApi> getGroups() {
        return groups;
    }

}
