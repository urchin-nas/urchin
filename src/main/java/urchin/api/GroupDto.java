package urchin.api;

public class GroupDto {

    private int groupId;
    private String name;

    private GroupDto() {
    }

    public GroupDto(int groupId, String name) {
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
