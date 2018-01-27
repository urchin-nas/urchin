import {Actions} from "../constants";

const aclReducer = (state = {}, action) => {
    switch (action.type) {
        case Actions.ACL.GET_ACL:
            return {...state, isFetchingAcl: true};

        case Actions.ACL.GET_ACL_SUCCESS:
            let aclGroups = toAclGroups(action.data.groups);
            let aclUsers = toAclUsers(action.data.users);
            return {...state, isFetchingAcl: false, acl: {groups: aclGroups, users: aclUsers}};

        default:
            return state;
    }
};

export default aclReducer


function toAclGroups(groups) {
    return groups.map(item => {
            return {
                groupId: item.group.groupId,
                groupName: item.group.groupName,
                read: item.aclPermissions.hasRead,
                write: item.aclPermissions.hasWrite,
                execute: item.aclPermissions.hasExecute,
            }
        }
    );
}

function toAclUsers(users) {
    return users.map(item => {
            return {
                userId: item.user.userId,
                username: item.user.username,
                read: item.aclPermissions.hasRead,
                write: item.aclPermissions.hasWrite,
                execute: item.aclPermissions.hasExecute,
            }
        }
    );
}