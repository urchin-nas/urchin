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
                <h2>userlist</h2>
                <ul>
                    {userItems}
                </ul>
                <a href="/users/0">new user</a>
            </div>
        )
    }
}

export default UserList