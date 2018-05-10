import React, {Component} from 'react'
import FieldError from "../FieldError";

class SetupAdmin extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setAdmin(data);
    };

    setupAdmin = () => {
        this.props.callbacks.setupAdmin(this.props.admin);
    };

    render() {
        let username = this.props.admin.username || '';

        return (
            <div data-view="setup"
                 className="setup">
                <h2>Setup admin</h2>
                <input name="username"
                       type="text"
                       placeholder="username"
                       value={username}
                       onChange={this.update}/>
                <FieldError fieldErrors={this.props.fieldErrors}
                            field="username"/>
                <button data-view="setupAdmin"
                        className="setup__add-btn"
                        onClick={this.setupAdmin}>
                    Create User
                </button>
            </div>
        )
    }
}

export default SetupAdmin