import history from '../history'
import {Actions} from '../constants'
import {get, post, del} from './restClient'

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

export const getUserData = (userId) => (dispatch) => {
    dispatch({
        type: User.GET_USER
    });
    get('/api/users/' + userId)
        .then(json => dispatch({
            type: User.GET_USER_SUCCESS,
            data: json
        }))
};

export const setUserData = (user) => (dispatch) => {
    dispatch({
        type: User.SET_USER,
        data: user
    });
};

export const saveUserData = (userId, user) => (dispatch) => {
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
        }, json => (
            console.log('failed: ' + json)
        ))
};

export const deleteUserData = (userId) => (dispatch) => {
    dispatch({
        type: User.DELETE_USER
    });
    del('/api/users/' + userId)
        .then(json => {
            dispatch({
                type: User.DELETE_USER_SUCCESS,
                data: json
            });
            history.push('/users')
        }, json => (
            console.log('failed: ' + json)
        ))

};