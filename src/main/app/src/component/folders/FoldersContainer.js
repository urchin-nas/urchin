import React, {Component} from 'react'
import {connect} from 'react-redux'
import {getFolders} from '../../action/folderAction'
import Folders from "./Folders";

export class FoldersContainer extends Component {

    componentWillMount() {
        this.props.getFolders();
    }

    render() {
        return (
            <Folders folders={this.props.folders}/>
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

export default connect(mapStateToProps, mapDispatchToProps)(FoldersContainer)