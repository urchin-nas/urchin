import React, {Component} from 'react'
import {connect} from 'react-redux'
import {getAcl, updateAclForGroup, updateAclForUser} from '../../../../action/aclAction';
import {getUsers} from "../../../../action/userAction";
import {getGroups} from "../../../../action/groupAction";
import EditAcl from "./EditAcl";

export class AclContainer extends Component {

    componentDidUpdate(prevProps, prevState) {
        if (this.props.folderId !== prevProps.folderId) {
            this.props.getAcl(this.props.folderId);
            this.props.getUsers();
            this.props.getGroups();
        }
    }

    filterOutUnmanagedUsers() {
        let userIds = this.props.acl.users.map(user => user.userId);

        return this.props.users.filter(user =>
            userIds.indexOf(user.userId) === -1
        ).map(user => {
            return {
                userId: user.userId,
                username: user.username,
                read: false,
                write: false,
                execute: false,
            }
        });
    }

    filterOutUnmanagedGroups() {
        let groupIds = this.props.acl.groups.map(group => group.groupId);

        return this.props.groups.filter(group =>
            groupIds.indexOf(group.groupId) === -1
        ).map(group => {
            return {
                groupId: group.groupId,
                groupName: group.groupName,
                read: false,
                write: false,
                execute: false,
            }
        });
    }

    getAclUsers() {
        return (this.props.acl.users || [])
            .concat(this.filterOutUnmanagedUsers())
            .sort((a, b) => {
                return a.username.localeCompare(b.username);
            });
    }

    getAclGroups() {
        return (this.props.acl.groups || [])
            .concat(this.filterOutUnmanagedGroups())
            .sort((a, b) => {
                return a.groupName.localeCompare(b.groupName);
            });
    }

    updateAclForUser = (userId, field, checked) => {
        let user = this.getAclUsers().find(user => user.userId === userId);
        let userAcl = {
            folderId: this.props.folderId,
            userId: user.userId,
            read: user.read,
            write: user.write,
            execute: user.execute,
        };
        userAcl[field] = checked;
        this.props.updateAclForUser(userAcl);
    };

    updateAclForGroup = (groupId, field, checked) => {
        let group = this.getAclGroups().find(group => group.groupId === groupId);
        let groupAcl = {
            folderId: this.props.folderId,
            groupId: group.groupId,
            read: group.read,
            write: group.write,
            execute: group.execute,
        };
        groupAcl[field] = checked;
        this.props.updateAclForGroup(groupAcl);
    };

    render() {
        let aclUsers = this.getAclUsers();
        let aclGroups = this.getAclGroups();

        return (
            <EditAcl aclUsers={aclUsers}
                     aclGroups={aclGroups}
                     callbacks={{
                         updateAclForUser: this.updateAclForUser,
                         updateAclForGroup: this.updateAclForGroup
                     }}/>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getAcl: (folderId) => {
            dispatch(getAcl(folderId))
        },
        updateAclForGroup: (groupAcl) => {
            dispatch(updateAclForGroup(groupAcl))
        },
        updateAclForUser: (userAcl) => {
            dispatch(updateAclForUser(userAcl))
        },
        getGroups: () => {
            dispatch(getGroups())
        },
        getUsers: () => {
            dispatch(getUsers());
        }
    }
};

const mapStateToProps = (state) => {
    return {
        acl: state.aclData.acl || {users: [], groups: []},
        users: state.userData.users || [],
        groups: state.groupData.groups || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(AclContainer)