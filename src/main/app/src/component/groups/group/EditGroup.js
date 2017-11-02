import React, {Component} from 'react'
import FieldError from "../../FieldError";
import history from "../../../history";

class Groups extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setGroup(data);
    };

    removeUser = (userId) => {
        this.props.callbacks.removeUser(this.props.group.groupId, userId)
    };

    del = () => {
        this.props.callbacks.deleteGroup(this.props.groupId);
    };

    back = () => {
        history.push('/groups');
    };

    render() {
        let groupName = this.props.group.groupName || '';
        let usersInGroup = this.props.usersInGroup.map((item, index) =>
            <li key={index.toString()}>
                <a href={`/users/${item.userId}`}>{item.username}</a>
                <button onClick={() => this.removeUser(item.userId)}>Remove User</button>
            </li>
        );
        return (

            <div>
                <h2>Group</h2>
                <input
                    name="groupName"
                    type="text"
                    value={groupName}
                    onChange={this.update}
                />
                <FieldError
                    fieldErrors={this.props.fieldErrors}
                    field="groupName"
                />
                <button onClick={this.del}>Delete</button>
                <button onClick={this.back}>Back</button>
                <h2>Member of groups</h2>
                <ul>
                    {usersInGroup}
                </ul>
            </div>
        )
    }
}

export default Groups