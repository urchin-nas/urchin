import {Actions} from '../constants'
import {get, post} from './restClient'
import {notifyBackendError, notifySuccess} from "./notificationAction";

const {ACL} = Actions;

export const getAcl = (folderId) => (dispatch) => {
    dispatch({
        type: ACL.GET_ACL
    });
    return get('/api/permissions/acl/' + folderId)
        .then(json => dispatch({
            type: ACL.GET_ACL_SUCCESS,
            data: json
        }))
};

export const updateAclForGroup = (groupAcl) => (dispatch) => {
    dispatch({
        type: ACL.UPDATE_GROUP_ACL
    });
    return post('/api/permissions/acl/group', groupAcl)
        .then(json => {
                dispatch({
                    type: ACL.UPDATE_GROUP_ACL_SUCCESS,
                    data: json
                });
                notifySuccess("Success", "Group permissions updated");
            return dispatch(getAcl(groupAcl.folderId))
            }, error => {
                notifyBackendError(error);
            }
        )
};

export const updateAclForUser = (userAcl) => (dispatch) => {
    dispatch({
        type: ACL.UPDATE_USER_ACL
    });
    return post('/api/permissions/acl/user', userAcl)
        .then(json => {
                dispatch({
                    type: ACL.UPDATE_USER_ACL_SUCCESS,
                    data: json
                });
                notifySuccess("Success", "User permissions updated");
            return dispatch(getAcl(userAcl.folderId));
            }, error => {
                notifyBackendError(error);
            }
        )
};
