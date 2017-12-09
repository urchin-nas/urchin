import React, {Component} from "react";
import history from "../../../history";
import {Link} from "react-router-dom";

class EditUser extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setUser(data);
    };

    addGroup = () => {
        this.props.callbacks.addGroup(this.props.user.userId, this.props.user.groupId)
    };

    removeGroup = (groupId) => {
        this.props.callbacks.removeGroup(this.props.user.userId, groupId)
    };

    del = () => {
        this.props.callbacks.deleteUser(this.props.user.userId);
    };

    back = () => {
        history.push('/users');
    };

    render() {
        let username = this.props.user.username;
        let groupsForUser = this.props.groupsForUser.map((group, index) =>
            <li className="edit-user__groups__item" key={index.toString()}>
                <Link to={`/groups/${group.groupId}`}>{group.groupName}</Link>
                <button className="edit-user__groups__item__remove-group-btn" onClick={() => this.removeGroup(group.groupId)}>
                    Remove Group
                </button>
            </li>
        );
        let availableGroups = this.props.availableGroups;

        return (
            <div id="edit-user">
                <h2>User</h2>
                <div>Username: {username}</div>
                <select
                    id="edit-user__available-groups"
                    name="groupId"
                    onChange={this.update}>
                    <option>-- select group --</option>
                    {availableGroups.map(group =>
                        <option className="edit-user__available-groups__item" key={group.groupId}
                                value={group.groupId}>{group.groupName}</option>
                    )}
                </select>
                <button id="edit-user__add-group-btn" onClick={this.addGroup}>Add Group</button>
                <button id="edit-user__delete-btn" onClick={this.del}>Delete</button>
                <button id="edit-user__back-btn" onClick={this.back}>Back</button>
                <h2>Member of groups</h2>
                <ul id="edit-user__groups">
                    {groupsForUser}
                </ul>
            </div>
        )
    }
}

export default EditUser