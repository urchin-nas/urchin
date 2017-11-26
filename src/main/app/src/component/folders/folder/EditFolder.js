import React, {Component} from 'react'
import history from "../../../history";

class Folders extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setFolder(data);
    };

    del = () => {
        this.props.callbacks.deleteFolder(this.props.folder.folderId);
    };

    back = () => {
        history.push('/folders');
    };

    render() {
        let folderName = this.props.folder.folderName;
        let folderPath = this.props.folder.folderPath;
        return (
            <div id="edit-folder">
                <h2>Folder</h2>
                <div>Name: {folderName}</div>
                <div>Path: {folderPath}</div>
                <button id="edit-folder__delete-btn" onClick={this.del}>Delete</button>
                <button id="edit-folder__back-btn" onClick={this.back}>Back</button>
            </div>
        )
    }
}

export default Folders