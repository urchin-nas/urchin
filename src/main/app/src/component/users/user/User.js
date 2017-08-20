import React, {Component} from 'react'

class Users extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setUser(data);
    };

    save = () => {
        this.props.callbacks.saveUser(this.props.userId || 0);
    };

    delete = () => {
        this.props.callbacks.deleteUser(this.props.userId);
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
                    value={username}
                    onChange={this.update}
                />
                <input
                    name="password"
                    type="password"
                    value={password}
                    onChange={this.update}
                />
                <button onClick={this.save}>Save</button>
                { this.props.userId > 0 &&
                <button onClick={this.delete}>Delete</button>
                }
            </div>
        )
    }
}

export default Users