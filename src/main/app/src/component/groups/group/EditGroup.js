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

    del = () => {
        this.props.callbacks.deleteGroup(this.props.groupId);
    };

    back = () => {
        history.push('/groups');
    };

    render() {
        let groupName = this.props.group.groupName || '';
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
            </div>
        )
    }
}

export default Groups