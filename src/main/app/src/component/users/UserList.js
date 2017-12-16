import React, {Component} from "react";
import {Link} from "react-router-dom";

class UserList extends Component {

    render() {
        let users = this.props.users || [];
        let userItems = users.map((item, index) =>
            <li className="users__user" key={index.toString()}>
                <Link to={`/users/${item.userId}`}>{item.username}</Link>
            </li>
        );
        return (
            <div id="user-list">
                <Link id="user-list__new-user" to="/users/new">new user</Link>
                <h2>Users</h2>
                <ul className="users">
                    {userItems}
                </ul>
            </div>
        )
    }
}

export default UserList