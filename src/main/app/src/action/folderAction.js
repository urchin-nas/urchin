import history from '../history'
import {Actions, ErrorCodes} from '../constants'
import {del, get, post} from './restClient'
import {notifyBackendError, notifySuccess} from "./notificationAction";

const {Folders} = Actions;
const {Folder} = Actions;

export const getFolders = () => (dispatch) => {
    dispatch({
        type: Folders.GET_FOLDERS
    });
    return get('/api/folders')
        .then(json => dispatch({
            type: Folders.GET_FOLDERS_SUCCESS,
            data: json
        }))
};

export const getFolder = (folderId) => (dispatch) => {
    dispatch({
        type: Folder.GET_FOLDER
    });
    return get('/api/folders/' + folderId)
        .then(json => dispatch({
            type: Folder.GET_FOLDER_SUCCESS,
            data: json
        }))
};

export const setFolder = (folder) => (dispatch) => {
    return dispatch({
        type: Folder.SET_FOLDER,
        data: folder
    });
};

export const createFolder = (folder) => (dispatch) => {
    dispatch({
        type: Folder.SAVE_FOLDER
    });
    return post('/api/folders/create', folder)
        .then(json => {
                dispatch({
                    type: Folder.SAVE_FOLDER_SUCCESS,
                    data: json
                });
            history.push('/folders/new/confirm');
                notifySuccess("Success", "Folder created")
            }, error => {
                if (error.errorCode === ErrorCodes.VALIDATION_ERROR) {
                    dispatch({
                        type: Folder.SAVE_FOLDER_VALIDATION_ERROR,
                        data: error
                    });
                } else {
                    notifyBackendError(error)
                }
            }
        )
};

export const deleteFolder = (folderId) => (dispatch) => {
    dispatch({
        type: Folder.DELETE_FOLDER
    });
    return del('/api/folders/' + folderId)
        .then(json => {
            dispatch({
                type: Folder.DELETE_FOLDER_SUCCESS,
                data: json
            });
            history.push('/folders');
            notifySuccess("Success", "Folder deleted")
        }, error => (
            notifyBackendError(error)
        ))
};

export const setEncryptedFolderPassphrase = (passphrase) => (dispatch) => {
    return dispatch({
        type: Folder.SET_CONFIRM_NEW_ENCRYPTED_FOLDER,
        data: passphrase
    });
};

export const confirmEncryptedFolder = (folderId, passphrase) => (dispatch) => {
    dispatch({
        type: Folder.CONFIRM_NEW_ENCRYPTED_FOLDER
    });
    let data = {
        folderId: folderId,
        passphrase: passphrase
    };
    return post('/api/folders/mount', data)
        .then(json => {
                dispatch({
                    type: Folder.CONFIRM_NEW_ENCRYPTED_FOLDER_SUCCESS,
                    data: json
                });
                history.push('/folders/' + folderId);
                notifySuccess("Success", "Folder confirmed and ready to use")
            }, error => {
                notifyBackendError(error)

            }
        )
};