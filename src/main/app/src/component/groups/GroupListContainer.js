import React, {Component} from 'react'
import {connect} from 'react-redux'
import {getGroups} from '../../action/groupAction'
import GroupList from "./GroupList";

class GroupListContainer extends Component {

    componentWillMount() {
        this.props.getGroups();
    }

    render() {
        return (
            <GroupList
                groups={this.props.groups}
            />
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getGroups: () => {
            dispatch(getGroups())
        }
    }
};

const mapStateToProps = (state) => {
    return {
        groups: state.groupData.groups || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(GroupListContainer)