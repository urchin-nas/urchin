import React, {Component} from 'react'
import {connect} from 'react-redux'
import ConfirmNewFolder from './ConfirmNewFolder';
import {confirmEncryptedFolder, setEncryptedFolderPassphrase} from '../../../action/folderAction';

export class ConfirmNewFolderContainer extends Component {

    render() {
        return (
            <ConfirmNewFolder confirmNewFolder={this.props.confirmNewFolder}
                              createdFolder={this.props.createdFolder}
                              fieldErrors={this.props.fieldErrors}
                              callbacks={{
                                  setEncryptedFolderPassphrase: this.props.setEncryptedFolderPassphrase,
                                  confirmEncryptedFolder: this.props.confirmEncryptedFolder
                              }}/>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setEncryptedFolderPassphrase: (passphrase) => {
            dispatch(setEncryptedFolderPassphrase(passphrase))
        },
        confirmEncryptedFolder: (folderId, passphrase) => {
            dispatch(confirmEncryptedFolder(folderId, passphrase))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        confirmNewFolder: state.folderData.confirmNewFolder || {},
        createdFolder: state.folderData.createdFolder,
        fieldErrors: state.folderData.fieldErrors || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ConfirmNewFolderContainer)