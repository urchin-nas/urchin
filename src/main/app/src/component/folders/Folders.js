import React, {Component} from "react";
import {Link} from "react-router-dom";

class Folders extends Component {

    render() {
        let folders = this.props.folders || [];
        let folderItems = folders.map((item, index) =>
            <li className="folder-list__folder"
                key={index.toString()}>
                <Link to={`/folders/${item.folderId}`}>
                    {item.folderName}
                </Link>
            </li>
        );
        return (
            <div data-view="folders"
                 className="folders">
                <Link data-view="newFolder"
                      className="folders__new-folder"
                      to="/folders/new">
                    new folder</Link>
                <h2>Folders</h2>
                <ul className="folder-list">
                    {folderItems}
                </ul>
            </div>
        )
    }
}

export default Folders