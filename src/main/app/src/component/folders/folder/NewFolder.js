import React, {Component} from 'react'
import FieldError from "../../FieldError"
import history from '../../../history'

class NewFolder extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setFolder(data);
    };

    create = () => {
        this.props.callbacks.createFolder(this.props.folder);
    };

    cancel = () => {
        history.push('/folders');
    };

    render() {
        let folder = this.props.folder.folder || '';

        return (
            <div className="new-folder">
                <h2>Folder</h2>
                <input
                    name="folder"
                    type="text"
                    value={folder}
                    onChange={this.update}
                />
                <FieldError
                    fieldErrors={this.props.fieldErrors}
                    field="folder"
                />
                <button onClick={this.create}>Create Folder</button>
                <button onClick={this.cancel}>Cancel</button>
            </div>
        )
    }
}

export default NewFolder