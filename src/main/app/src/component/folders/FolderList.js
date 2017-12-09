import React, {Component} from "react";
import {Link} from "react-router-dom";

class FolderList extends Component {

    render() {
        let folders = this.props.folders || [];
        let folderItems = folders.map((item, index) =>
            <li key={index.toString()}>
                <Link to={`/folders/${item.folderId}`}>{item.folderName}</Link>
            </li>
        );
        return (
            <div id="folder-list">
                <Link id="folder-list__new-folder" to="/folders/new">new folder</Link>
                <h2>Folders</h2>
                <ul>
                    {folderItems}
                </ul>
            </div>
        )
    }
}

export default FolderList