import reducer from './folderReducer';
import {Actions} from "../constants";

describe('folderReducer', () => {

    const folder = {
        folderId: 1,
        folderName: 'folder',
        folderPath: '/folder'
    };

    it('initial state', () => {

        expect(reducer(undefined, {})).toMatchSnapshot();
    });

    it('GET_FOLDERS', () => {

        expect(reducer({}, {type: Actions.Folders.GET_FOLDERS})).toMatchSnapshot();
    });

    it('GET_FOLDERS_SUCCESS', () => {

        const action = {
            type: Actions.Folders.GET_FOLDERS_SUCCESS,
            data: [folder]
        };

        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('GET_FOLDER', () => {

        expect(reducer({}, {type: Actions.Folder.GET_FOLDER})).toMatchSnapshot();
    });


    it('GET_FOLDER_SUCCESS', () => {

        const action = {
            type: Actions.Folder.GET_FOLDER_SUCCESS,
            data: folder
        };

        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SET_FOLDER', () => {

        const action = {
            type: Actions.Folder.SET_FOLDER,
            data: {
                field: 'path',
                value: '/folder'
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SAVE_FOLDER', () => {

        expect(reducer({}, {type: Actions.Folder.SAVE_FOLDER})).toMatchSnapshot();
    });

    it('SAVE_FOLDER_SUCCESS', () => {

        const action = {
            type: Actions.Folder.SAVE_FOLDER_SUCCESS,
            data: {
                id: 1,
                passphrase: 'randomString'
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SAVE_FOLDER_VALIDATION_ERROR', () => {

        const action = {
            type: Actions.Folder.SAVE_FOLDER_VALIDATION_ERROR,
            data: {
                errorCode: 'VALIDATION_ERROR',
                message: 'validation error',
                fieldErrors: {
                    folder: [
                        'Folder path must be absolute'
                    ]
                }
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SET_CONFIRM_NEW_ENCRYPTED_FOLDER', () => {

        const action = {
            type: Actions.Folder.SET_CONFIRM_NEW_ENCRYPTED_FOLDER,
            data: {
                field: 'passphrase',
                value: 'randomString'
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

});
