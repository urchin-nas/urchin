import React, {Component} from 'react'
import history from "../../../history";

class Groups extends Component {

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
            <li key={index.toString()}>
                <a href={`/users/${user.userId}`}>{user.username}</a>
                <button onClick={() => this.removeUser(user.userId)}>Remove User</button>
            </li>
        );
        let availableUsers = this.props.availableUsers;

        return (
            <div id="edit-group">
                <h2>Group</h2>
                <div>name: {groupName}</div>
                <select
                    name="userId"
                    onChange={this.update}>
                    <option>-- select user --</option>
                    {availableUsers.map(user =>
                        <option key={user.userId} value={user.userId}>{user.username}</option>
                    )}
                </select>
                <button id="edit-group__add-group-btn" onClick={this.addUser}>Add User</button>
                <button id="edit-group__delete-btn" onClick={this.del}>Delete</button>
                <button id="edit-user__back-btn" onClick={this.back}>Back</button>
                <h2>Members of group</h2>
                <ul>
                    {usersInGroup}
                </ul>
            </div>
        )
    }
}

export default Groups