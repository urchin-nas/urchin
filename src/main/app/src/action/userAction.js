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
    get('/api/users')
        .then(json => dispatch({
            type: Users.GET_USERS_SUCCESS,
            data: json
        }))
};

export const getUser = (userId) => (dispatch) => {
    dispatch({
        type: User.GET_USER
    });
    get('/api/users/' + userId)
        .then(json => dispatch({
            type: User.GET_USER_SUCCESS,
            data: json
        }))
};

export const setUser = (user) => (dispatch) => {
    dispatch({
        type: User.SET_USER,
        data: user
    });
};

export const createUser = (user) => (dispatch) => {
    dispatch({
        type: User.SAVE_USER
    });
    post('/api/users/add', user)
        .then(json => {
            dispatch({
                type: User.SAVE_USER_SUCCESS,
                data: json
            });
            history.push('/users');
            notifySuccess("Success", "User created")
        }, error => {
            if (error.errorCode === ErrorCodes.VALIDATION_ERROR) {
                dispatch({
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
    del('/api/users/' + userId)
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

export const addGroup = (userId, groupId) => (dispatch) => {
    let body = {
        userId: userId,
        groupId: groupId
    };
    dispatch({
        type: User.ADD_GROUP
    });
    post('/api/groups/user', body)
        .then(json => {
            console.log('success: ' + json);
            dispatch({
                type: User.ADD_GROUP_SUCCESS,
                data: json
            });
            history.push('/users');
            notifySuccess("Success", "User added to group")
        }, error => (
            notifyBackendError(error)
        ))
};