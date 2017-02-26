package urchin.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

public class Group {

    private final GroupId groupId;
    private final String name;
    private final LocalDateTime created;

    public Group(String name) {
        this.groupId = null;
        this.name = name.toLowerCase().trim();
        this.created = null;
    }

    public Group(GroupId groupId, String name, LocalDateTime created) {
        this.groupId = groupId;
        this.name = name.toLowerCase().trim();
        this.created = created;
    }

    public GroupId getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
