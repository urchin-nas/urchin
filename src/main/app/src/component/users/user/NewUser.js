import React, {Component} from "react";
import history from '../../../history'

class NewUser extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setUser(data);
    };

    create = () => {
        this.props.callbacks.createUser(this.props.user);
    };

    cancel = () => {
        history.push('/users');
    };

    render() {
        let username = this.props.user.username || '';
        let password = this.props.user.password || '';
        return (
            <div>
                <h2>User</h2>
                <input
                    name="username"
                    type="text"
                    placeholder="username"
                    value={username}
                    onChange={this.update}
                />
                <input
                    name="password"
                    type="password"
                    placeholder="password"
                    value={password}
                    onChange={this.update}
                />
                <button onClick={this.create}>Create User</button>
                <button onClick={this.cancel}>Cancel</button>
            </div>
        )
    }
}

export default NewUser