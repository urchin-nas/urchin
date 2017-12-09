import React, {Component} from "react";
import {Link} from "react-router-dom";

class GroupList extends Component {

    render() {
        let groups = this.props.groups || [];
        let groupItems = groups.map((item, index) =>
            <li key={index.toString()}>
                <Link to={`/groups/${item.groupId}`}>{item.groupName}</Link>
            </li>
        );
        return (
            <div id="group-list">
                <Link id="group-list__new-group" to="/groups/new">new group</Link>
                <h2>grouplist</h2>
                <ul>
                    {groupItems}
                </ul>
            </div>
        )
    }
}

export default GroupList