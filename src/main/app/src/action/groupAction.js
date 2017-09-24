import history from '../history'
import {Actions, ErrorCodes} from '../constants'
import {del, get, post} from './restClient'
import {notifyBackendError, notifySuccess} from "./notificationAction";

const {Groups} = Actions;
const {Group} = Actions;

export const getGroups = () => (dispatch) => {
    dispatch({
        type: Groups.GET_GROUPS
    });
    get('/api/groups')
        .then(json => dispatch({
            type: Groups.GET_GROUPS_SUCCESS,
            data: json
        }))
};

export const getGroup = (groupId) => (dispatch) => {
    dispatch({
        type: Group.GET_GROUP
    });
    get('/api/groups/' + groupId)
        .then(json => dispatch({
            type: Group.GET_GROUP_SUCCESS,
            data: json
        }))
};

export const setGroup = (group) => (dispatch) => {
    dispatch({
        type: Group.SET_GROUP,
        data: group
    });
};

export const createGroup = (group) => (dispatch) => {
    dispatch({
        type: Group.SAVE_GROUP
    });
    post('/api/groups/add', group)
        .then(json => {
                dispatch({
                    type: Group.SAVE_GROUP_SUCCESS,
                    data: json
                });
                history.push('/groups');
                notifySuccess("Success", "Group created")
            }, error => {
                if (error.errorCode === ErrorCodes.VALIDATION_ERROR) {
                    dispatch({
                        type: Group.SAVE_GROUP_VALIDATION_ERROR,
                        data: error
                    });
                } else {
                    notifyBackendError(error)
                }
            }
        )
};

export const deleteGroup = (groupId) => (dispatch) => {
    dispatch({
        type: Group.DELETE_GROUP
    });
    del('/api/groups/' + groupId)
        .then(json => {
            dispatch({
                type: Group.DELETE_GROUP_SUCCESS,
                data: json
            });
            history.push('/groups');
            notifySuccess("Success", "Group deleted")
        }, error => (
            notifyBackendError(error)
        ))
};