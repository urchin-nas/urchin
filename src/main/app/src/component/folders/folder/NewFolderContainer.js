import React, {Component} from 'react'
import {connect} from 'react-redux'
import NewFolder from './NewFolder';
import {createFolder, setFolder} from '../../../action/folderAction';

export class NewFolderContainer extends Component {

    render() {
        return (
            <NewFolder folder={this.props.folder}
                       fieldErrors={this.props.fieldErrors}
                       callbacks={{
                           setFolder: this.props.setFolder,
                           createFolder: this.props.createFolder,
                       }}/>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setFolder: (folder) => {
            dispatch(setFolder(folder))
        },
        createFolder: (folder) => {
            dispatch(createFolder(folder))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        folder: state.folderData.folder || {},
        fieldErrors: state.folderData.fieldErrors || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NewFolderContainer)