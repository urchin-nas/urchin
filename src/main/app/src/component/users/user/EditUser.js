import React, {Component} from "react";
import history from "../../../history";

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

    del = () => {
        this.props.callbacks.deleteUser(this.props.user.userId);
    };

    back = () => {
        history.push('/users');
    };

    render() {
        let username = this.props.user.username;
        let groupsForUser = this.props.groupsForUser.map((item, index) =>
            <li key={index.toString()}>
                <a href={`/groups/${item.groupId}`}>{item.groupName}</a>
            </li>
        );
        let availableGroups = this.props.availableGroups;

        return (
            <div>
                <h2>User</h2>
                <div>Username: {username}</div>
                <select
                    name="groupId"
                    onChange={this.update}>
                    <option> -- select group --</option>
                    {availableGroups.map(group =>
                        <option key={group.groupId} value={group.groupId}>{group.groupName}</option>
                    )}
                </select>
                <button onClick={this.addGroup}>Add Group</button>
                <button onClick={this.del}>Delete</button>
                <button onClick={this.back}>Back</button>
                <h2>Member of groups</h2>
                <ul>
                    {groupsForUser}
                </ul>
            </div>
        )
    }
}

export default EditUser