import {Actions} from "../constants";

const folderReducer = (state = {}, action) => {
    switch (action.type) {
        case Actions.Folders.GET_FOLDERS:
            return {...state, isFetchingFolders: true};

        case Actions.Folders.GET_FOLDERS_SUCCESS:
            return {...state, isFetchingFolders: false, folders: action.data};

        case Actions.Folder.GET_FOLDER:
            return {...state, isFetchingFolder: true};

        case Actions.Folder.GET_FOLDER_SUCCESS:
            return {...state, isFetchingFolder: false, folder: action.data};

        case Actions.Folder.SET_FOLDER:
            return {...state, folder: {...state.folder, [action.data.field]: action.data.value}};

        case Actions.Folder.SAVE_FOLDER:
            return {...state, isSavingFolder: true};

        case Actions.Folder.SAVE_FOLDER_SUCCESS:
            return {...state, isSavingFolder: false, createdFolder: action.data};

        case Actions.Folder.SAVE_FOLDER_VALIDATION_ERROR:
            return {...state, fieldErrors: action.data.fieldErrors};

        case Actions.Folder.SET_CONFIRM_NEW_ENCRYPTED_FOLDER:
            return {...state, confirmNewFolder: {...state.confirmNewFolder, [action.data.field]: action.data.value}};

        default:
            return state;
    }
};

export default folderReducer