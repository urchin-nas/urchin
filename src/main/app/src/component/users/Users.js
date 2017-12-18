import React, {Component} from "react";
import {Link} from "react-router-dom";

class Users extends Component {

    render() {
        let users = this.props.users || [];
        let userItems = users.map((item, index) =>
            <li className="user-list__user"
                key={index.toString()}>
                <Link to={`/users/${item.userId}`}>
                    {item.username}
                </Link>
            </li>
        );
        return (
            <div data-view="users"
                 className="users">
                <Link data-view="newUser"
                      className="users__new-user"
                      to="/users/new">
                    new user
                </Link>
                <h2>Users</h2>
                <ul className="user-list">
                    {userItems}
                </ul>
            </div>
        )
    }
}

export default Users