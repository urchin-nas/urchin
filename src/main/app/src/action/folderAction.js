import history from '../history'
import {Actions, ErrorCodes} from '../constants'
import {get, post} from './restClient'
import {notifyBackendError, notifySuccess} from "./notificationAction";

const {Folders} = Actions;
const {Folder} = Actions;

export const getFolders = () => (dispatch) => {
    dispatch({
        type: Folders.GET_FOLDERS
    });
    get('/api/folders')
        .then(json => dispatch({
            type: Folders.GET_FOLDERS_SUCCESS,
            data: json
        }))
};

export const getFolder = (folderId) => (dispatch) => {
    dispatch({
        type: Folder.GET_FOLDER
    });
    get('/api/folders/' + folderId)
        .then(json => dispatch({
            type: Folder.GET_FOLDER_SUCCESS,
            data: json
        }))
};

export const setFolder = (folder) => (dispatch) => {
    dispatch({
        type: Folder.SET_FOLDER,
        data: folder
    });
};

export const createFolder = (folder) => (dispatch) => {
    dispatch({
        type: Folder.SAVE_FOLDER
    });
    post('/api/folders/create', folder)
        .then(json => {
                dispatch({
                    type: Folder.SAVE_FOLDER_SUCCESS,
                    data: json
                });
                history.push('/folders');
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