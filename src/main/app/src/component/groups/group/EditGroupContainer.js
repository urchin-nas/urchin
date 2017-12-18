import React, {Component} from 'react'
import {connect} from 'react-redux'
import EditGroup from './EditGroup';
import {addUser, deleteGroup, getGroup, getUsersForGroup, removeUser, setGroup} from '../../../action/groupAction';
import {getUsers} from "../../../action/userAction";

export class EditGroupContainer extends Component {

    componentWillMount() {
        let groupId = parseInt(this.props.match.params.id, 10);
        this.props.getGroup(groupId);
        this.props.getUsersForGroup(groupId);
        this.props.getUsers();
    }

    filterOutAvailableUsers() {
        let userIds = this.props.usersInGroup.map(user => user.userId);

        return this.props.users.filter(user =>
            userIds.indexOf(user.userId) === -1
        );
    }

    render() {
        let availableUsers = this.filterOutAvailableUsers();

        return (
            <EditGroup group={this.props.group}
                       usersInGroup={this.props.usersInGroup}
                       availableUsers={availableUsers}
                       callbacks={{
                           setGroup: this.props.setGroup,
                           deleteGroup: this.props.deleteGroup,
                           addUser: this.props.addUser,
                           removeUser: this.props.removeUser,
                       }}/>
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
        },
        getUsers: () => {
            dispatch(getUsers());
        }
    }
};

const mapStateToProps = (state) => {
    return {
        group: state.groupData.group || {},
        usersInGroup: state.groupData.usersInGroup || [],
        users: state.userData.users || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(EditGroupContainer)