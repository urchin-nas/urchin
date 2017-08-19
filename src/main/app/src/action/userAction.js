import {Actions} from '../constants'
import {get, post} from './restClient'

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
    return post('/api/users/add', user)
        .then(json => dispatch({
            type: User.SAVE_USER_SUCCESS,
            data: json
        }))

};