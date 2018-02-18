import React, {Component} from 'react'
import history from "../../../history";
import {Link} from "react-router-dom";

class EditGroup extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setGroup(data);
    };

    addUser = () => {
        this.props.callbacks.addUser(this.props.group.groupId, this.props.group.userId);
    };

    removeUser = (userId) => {
        this.props.callbacks.removeUser(this.props.group.groupId, userId)
    };

    del = () => {
        this.props.callbacks.deleteGroup(this.props.group.groupId);
    };

    back = () => {
        history.push('/groups');
    };

    render() {
        let groupName = this.props.group.groupName;
        let usersInGroup = this.props.usersInGroup.map((user, index) =>
            <li data-view="users"
                className="edit-group__users__item"
                key={index.toString()}>
                <Link to={`/users/${user.userId}`}>{user.username}</Link>
                <button data-view="removeUser"
                        className="edit-group__users__item__remove-user-btn"
                        onClick={() => this.removeUser(user.userId)}>
                    Remove User
                </button>
            </li>
        );
        let availableUsers = this.props.availableUsers;

        return (
            <div data-view="editGroup"
                 className="edit-group">
                <h2>Group</h2>
                <div>name: {groupName}</div>
                <select data-view="availableUsers"
                        className="edit-group__available-users"
                        name="userId"
                        onChange={this.update}>
                    <option>-- select user --</option>
                    {availableUsers.map(user =>
                        <option className="edit-group__available-users__item"
                                key={user.userId}
                                value={user.userId}>
                            {user.username}
                        </option>
                    )}
                </select>
                <button data-view="addUser"
                        className="edit-group__add-user-btn"
                        onClick={this.addUser}>
                    Add User
                </button>
                <button data-view="delete"
                        className="edit-group__delete-btn"
                        onClick={this.del}>
                    Delete
                </button>
                <button data-view="back"
                        className="edit-group__back-btn"
                        onClick={this.back}>
                    Back
                </button>
                <h2>Members of group</h2>
                <ul className="edit-group__users">
                    {usersInGroup}
                </ul>
            </div>
        )
    }
}

export default EditGroup