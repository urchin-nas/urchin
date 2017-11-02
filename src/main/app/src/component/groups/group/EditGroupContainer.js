import React, {Component} from 'react'
import {connect} from 'react-redux'
import EditGroup from './EditGroup';
import {addUser, deleteGroup, getGroup, getUsersForGroup, removeUser, setGroup} from '../../../action/groupAction';

class EditGroupContainer extends Component {

    componentWillMount() {
        this.props.getGroup(this.getGroupId());
        this.props.getUsersForGroup(this.getGroupId());
    }

    setGroup = (group) => {
        this.props.setGroup(group);
    };

    deleteGroup = (groupId) => {
        this.props.deleteGroup(groupId);
    };

    getGroupId = () => {
        return parseInt(this.props.match.params.id, 10);
    };

    render() {
        let groupId = this.getGroupId();
        return (
            <EditGroup
                groupId={groupId}
                group={this.props.group}
                usersInGroup={this.props.usersInGroup}
                fieldErrors={this.props.fieldErrors}
                callbacks={{
                    setGroup: this.setGroup,
                    deleteGroup: this.deleteGroup,
                    addUser: this.props.addUser,
                    removeUser: this.props.removeUser,
                }}
            />
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getGroup: (groupId) => {
            dispatch(getGroup(groupId))
        },
        setGroup: (group) => {
            dispatch(setGroup(group))
        },
        deleteGroup: (groupId) => {
            dispatch(deleteGroup(groupId))
        },
        getUsersForGroup: (groupId) => {
            dispatch(getUsersForGroup(groupId))
        },
        addUser: (groupId, userId) => {
            dispatch(addUser(groupId, userId))
        },
        removeUser: (groupId, userId) => {
            dispatch(removeUser(groupId, userId))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        group: state.groupData.group || {},
        usersInGroup: state.groupData.usersInGroup || [],
        fieldErrors: state.groupData.fieldErrors || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(EditGroupContainer)