import history from '../history'
import {Actions, ErrorCodes} from '../constants'
import {post} from './restClient'
import {notifyBackendError, notifySuccess} from './notificationAction'

const {Admin} = Actions;


export const setAdmin = (admin) => (dispatch) => {
    return dispatch({
        type: Admin.SET_ADMIN,
        data: admin
    });
};

export const setupAdmin = (admin) => (dispatch) => {
    dispatch({
        type: Admin.SETUP_ADMIN
    });
    return post('/api/authenticate/add-first-admin', admin)
        .then(json => {
            dispatch({
                type: Admin.SETUP_ADMIN_SUCCESS,
                data: json
            });
            history.push('/login');
            notifySuccess("Success", "Admin setup")
        }, error => {
            if (error.errorCode === ErrorCodes.VALIDATION_ERROR) {
                return dispatch({
                    type: Admin.SETUP_ADMIN_VALIDATION_ERROR,
                    data: error
                });
            } else {
                notifyBackendError(error)
            }
        })
};