import React, {Component} from "react";
import {connect} from "react-redux";
import EditUser from "./EditUser";
import {addGroup, deleteUser, getGroupsForUser, getUser, removeGroup, setUser} from "../../../action/userAction";
import {getGroups} from "../../../action/groupAction";

export class EditUserContainer extends Component {

    componentWillMount() {
        let userId = parseInt(this.props.match.params.id, 10);
        this.props.getUser(userId);
        this.props.getGroupsForUser(userId);
        this.props.getGroups();
    }

    filterOutAvailableGroups() {
        let groupIds = this.props.groupsForUser.map(group => group.groupId);

        return this.props.groups.filter(group =>
            groupIds.indexOf(group.groupId) === -1
        );
    }

    render() {
        let availableGroups = this.filterOutAvailableGroups();

        return (
            <EditUser user={this.props.user}
                      groupsForUser={this.props.groupsForUser}
                      availableGroups={availableGroups}
                      callbacks={{
                          addGroup: this.props.addGroup,
                          removeGroup: this.props.removeGroup,
                          deleteUser: this.props.deleteUser,
                          setUser: this.props.setUser
                      }}/>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getUser: (userId) => {
            dispatch(getUser(userId))
        },
        setUser: (user) => {
            dispatch(setUser(user))
        },
        deleteUser: (userId) => {
            dispatch(deleteUser(userId))
        },
        getGroupsForUser: (userId) => {
            dispatch(getGroupsForUser(userId))
        },
        getGroups: () => {
            dispatch(getGroups())
        },
        addGroup: (userId, groupId) => {
            dispatch(addGroup(userId, groupId))
        },
        removeGroup: (userId, groupId) => {
            dispatch(removeGroup(userId, groupId))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        user: state.userData.user || {},
        groupsForUser: state.userData.groupsForUser || [],
        groups: state.groupData.groups || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(EditUserContainer)