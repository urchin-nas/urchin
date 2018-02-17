import history from '../history'
import {Actions, ErrorCodes} from '../constants'
import {del, get, post} from './restClient'
import {notifyBackendError, notifySuccess} from './notificationAction'

const {Users} = Actions;
const {User} = Actions;

export const getUsers = () => (dispatch) => {
    dispatch({
        type: Users.GET_USERS
    });
    return get('/api/users')
        .then(json => dispatch({
            type: Users.GET_USERS_SUCCESS,
            data: json
        }))
};

export const getUser = (userId) => (dispatch) => {
    dispatch({
        type: User.GET_USER
    });
    return get('/api/users/' + userId)
        .then(json => dispatch({
            type: User.GET_USER_SUCCESS,
            data: json
        }))
};

export const setUser = (user) => (dispatch) => {
    return dispatch({
        type: User.SET_USER,
        data: user
    });
};

export const createUser = (user) => (dispatch) => {
    dispatch({
        type: User.SAVE_USER
    });
    return post('/api/users/add', user)
        .then(json => {
            dispatch({
                type: User.SAVE_USER_SUCCESS,
                data: json
            });
            history.push('/users');
            notifySuccess("Success", "User created")
        }, error => {
            if (error.errorCode === ErrorCodes.VALIDATION_ERROR) {
                return dispatch({
                    type: User.SAVE_USER_VALIDATION_ERROR,
                    data: error
                });
            } else {
                notifyBackendError(error)
            }
        })
};

export const deleteUser = (userId) => (dispatch) => {
    dispatch({
        type: User.DELETE_USER
    });
    return del('/api/users/' + userId)
        .then(json => {
            dispatch({
                type: User.DELETE_USER_SUCCESS,
                data: json
            });
            history.push('/users');
            notifySuccess("Success", "User deleted")
        }, error => (
            notifyBackendError(error)
        ))
};

export const getGroupsForUser = (userId) => (dispatch) => {
    dispatch({
        type: User.GET_GROUPS_FOR_USER
    });
    return get('/api/users/' + userId + "/groups")
        .then(json => dispatch({
            type: User.GET_GROUPS_FOR_USER_SUCCESS,
            data: json
        }))
};

export const addGroup = (userId, groupId) => (dispatch) => {
    let body = {
        userId: userId,
        groupId: groupId
    };
    dispatch({
        type: User.ADD_GROUP
    });
    return post('/api/groups/user', body)
        .then(json => {
            dispatch({
                type: User.ADD_GROUP_SUCCESS,
                data: json
            });
            notifySuccess("Success", "User added to group");
            return dispatch(getGroupsForUser(userId));
        }, error => (
            notifyBackendError(error)
        ))
};

export const removeGroup = (userId, groupId) => (dispatch) => {
    dispatch({
        type: User.REMOVE_GROUP
    });
    return del('/api/groups/' + groupId + "/user/" + userId)
        .then(json => {
            dispatch({
                type: User.REMOVE_GROUP_SUCCESS,
                data: json
            });
            notifySuccess("Success", "User was removed from group");
            return dispatch(getGroupsForUser(userId));
        }, error => (
            notifyBackendError(error)
        ))
};