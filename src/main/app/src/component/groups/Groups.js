import React, {Component} from "react";
import {Link} from "react-router-dom";

class Groups extends Component {

    render() {
        let groups = this.props.groups || [];
        let groupItems = groups.map((item, index) =>
            <li className="group-list__group"
                key={index.toString()}>
                <Link to={`/groups/${item.groupId}`}>
                    {item.groupName}
                </Link>
            </li>
        );
        return (
            <div data-view="groups"
                 className="groups">
                <Link data-view="newGroup"
                      className="groups__new-group"
                      to="/groups/new">
                    new group</Link>
                <h2>grouplist</h2>
                <ul className="group-list">
                    {groupItems}
                </ul>
            </div>
        )
    }
}

export default Groups