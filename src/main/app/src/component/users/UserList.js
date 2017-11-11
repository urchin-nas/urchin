import React, {Component} from "react";

class UserList extends Component {

    render() {
        let users = this.props.users || [];
        let userItems = users.map((item, index) =>
            <li key={index.toString()}>
                <a href={`/users/${item.userId}`}>{item.username}</a>
            </li>
        );
        return (
            <div>
                <a href="/users/new">new user</a>
                <h2>Users</h2>
                <ul>
                    {userItems}
                </ul>
            </div>
        )
    }
}

export default UserList