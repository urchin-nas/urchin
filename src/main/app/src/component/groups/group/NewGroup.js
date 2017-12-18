import React, {Component} from 'react'
import FieldError from "../../FieldError"
import history from '../../../history'

class NewGroup extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setGroup(data);
    };

    create = () => {
        this.props.callbacks.createGroup(this.props.group);
    };

    cancel = () => {
        history.push('/groups');
    };

    render() {
        let groupName = this.props.group.groupName || '';
        return (

            <div data-view="newGroup"
                 className="new-group">
                <h2>Group</h2>
                <input name="groupName"
                       type="text"
                       value={groupName}
                       onChange={this.update}/>
                <FieldError
                    fieldErrors={this.props.fieldErrors}
                    field="groupName"/>
                <button data-view="create"
                        className="new-group__create-btn"
                        onClick={this.create}>
                    Create Group
                </button>
                <button data-view="cancel"
                        className="new-group__cancel-btn"
                        onClick={this.cancel}>
                    Cancel
                </button>
            </div>
        )
    }
}

export default NewGroup