import React, {Component} from 'react'
import {connect} from 'react-redux'
import EditFolder from './EditFolder';
import {deleteFolder, getFolder} from '../../../action/folderAction';

export class EditFolderContainer extends Component {

    componentWillMount() {
        let folderId = parseInt(this.props.match.params.id, 10);
        this.props.getFolder(folderId);
    }

    render() {
        return (
            <EditFolder folder={this.props.folder}
                        callbacks={{
                            deleteFolder: this.props.deleteFolder
                        }}/>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getFolder: (folderId) => {
            dispatch(getFolder(folderId))
        },
        deleteFolder: (folderId) => {
            dispatch(deleteFolder(folderId))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        folder: state.folderData.folder || {},
        usersInFolder: state.folderData.usersInFolder || [],
        users: state.userData.users || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(EditFolderContainer)