package urchin.api;

public class GroupApi {

    private int groupId;
    private String name;

    private GroupApi() {
    }

    public GroupApi(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

}
