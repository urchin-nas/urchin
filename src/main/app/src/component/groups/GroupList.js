import React, {Component} from "react";

class GroupList extends Component {

    render() {
        let groups = this.props.groups || [];
        let groupItems = groups.map((item, index) =>
            <li key={index.toString()}>
                <a href={`/groups/${item.groupId}`}>{item.groupName}</a>
            </li>
        );
        return (
            <div id="group-list">
                <a id="group-list__new-group" href="/groups/new">new group</a>
                <h2>grouplist</h2>
                <ul>
                    {groupItems}
                </ul>
            </div>
        )
    }
}

export default GroupList