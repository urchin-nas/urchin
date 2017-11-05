import React, {Component} from 'react'
import {connect} from 'react-redux'
import {getFolders} from '../../action/folderAction'
import FolderList from "./FolderList";

export class FolderListContainer extends Component {

    componentWillMount() {
        this.props.getFolders();
    }

    render() {
        return (
            <FolderList
                folders={this.props.folders}
            />
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getFolders: () => {
            dispatch(getFolders())
        }
    }
};

const mapStateToProps = (state) => {
    return {
        folders: state.folderData.folders || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(FolderListContainer)