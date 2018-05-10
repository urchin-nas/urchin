import 'isomorphic-fetch';
import {get} from './restClient'
import {Actions} from "../constants";

const {Auth} = Actions;

export const login = (username, password) => (dispatch) => {
    dispatch({
        type: Auth.LOGIN
    });

    return fetch('/login', {
        method: 'post',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
        },
        credentials: 'include',
        body: 'username=' + username + '&password=' + encodeURIComponent(password)
    }).then(response => {
        if (response.status >= 500) {
            dispatch({
                type: Auth.LOGIN_FAILED
            });
        } else if (response.status === 200) {
            dispatch({
                type: Auth.LOGIN_SUCCESS
            });
        } else {
            dispatch({
                type: Auth.LOGIN_INVALID
            });
        }
    }, error => {
        dispatch({
            type: Auth.LOGIN_FAILED,
            data: error
        });
    })
};

export const isAuthenticated = () => (dispatch) => {
    return get('/api/authenticate')
        .then(json => {
            dispatch({
                type: Auth.LOGIN_SUCCESS
            });
        }, error => {
            if (error.httpStatus === 428) {
                dispatch({
                    type: Auth.PRECONDITION_FAILED
                });
            } else {
                dispatch({
                    type: Auth.LOGOUT_SUCCESS
                });
            }
        })
};