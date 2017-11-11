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

    back = () => {
        history.push('/folders');
    };

    render() {
        let folderName = this.props.folder.folderName;
        let folderPath = this.props.folder.folderPath;
        return (
            <div>
                <h2>Folder</h2>
                <div>Name: {folderName}</div>
                <div>Path: {folderPath}</div>
                <button onClick={this.back}>Back</button>
            </div>
        )
    }
}

export default Folders