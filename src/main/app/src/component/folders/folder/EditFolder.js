import React, {Component} from 'react'
import history from "../../../history";
import AclContainer from "./acl/AclContainer";

class EditFolder extends Component {

    del = () => {
        this.props.callbacks.deleteFolder(this.props.folder.folderId);
    };

    back = () => {
        history.push('/folders');
    };

    render() {
        let folderName = this.props.folder.folderName;
        let folderPath = this.props.folder.folderPath;
        let folderId = this.props.folder.folderId;

        return (
            <div data-view="editFolder"
                 className="edit-folder">
                <h2>Folder</h2>
                <div>Name: {folderName}</div>
                <div>Path: {folderPath}</div>
                <button data-view="delete"
                        className="edit-folder__delete-btn"
                        onClick={this.del}>
                    Delete
                </button>
                <button data-view="back"
                        className="edit-folder__back-btn"
                        onClick={this.back}>
                    Back
                </button>
                <AclContainer folderId={folderId}/>
            </div>
        )
    }
}

export default EditFolder