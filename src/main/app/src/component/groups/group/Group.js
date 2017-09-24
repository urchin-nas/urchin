import React, {Component} from 'react'
import FieldError from "../../FieldError";

class Groups extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setGroup(data);
    };

    save = () => {
        this.props.callbacks.saveGroup(this.props.groupId || 0);
    };

    del = () => {
        this.props.callbacks.deleteGroup(this.props.groupId);
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
                <button onClick={this.save}>Save</button>
                { this.props.groupId > 0 &&
                <button onClick={this.del}>Delete</button>
                }
            </div>
        )
    }
}

export default Groups