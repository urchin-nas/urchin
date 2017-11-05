import React, {Component} from "react";

class FolderList extends Component {

    render() {
        let folders = this.props.folders || [];
        let folderItems = folders.map((item, index) =>
            <li key={index.toString()}>
                <a href={`/folders/${item.folderId}`}>{item.folderName}</a>
            </li>
        );
        return (
            <div>
                <h2>Folders</h2>
                <ul>
                    {folderItems}
                </ul>
                <a href="/folders/new">new folder</a>
            </div>
        )
    }
}

export default FolderList