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
                <a href="/folders/new">new folder</a>
                <h2>Folders</h2>
                <ul>
                    {folderItems}
                </ul>
            </div>
        )
    }
}

export default FolderList