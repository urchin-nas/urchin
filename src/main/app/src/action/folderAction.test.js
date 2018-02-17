import thunk from 'redux-thunk'
import configureMockStore from 'redux-mock-store'
import fetchMock from 'fetch-mock'
import {
    confirmEncryptedFolder,
    createFolder,
    deleteFolder,
    getFolder,
    getFolders,
    setEncryptedFolderPassphrase,
    setFolder
} from "./folderAction";

describe('folderAction', () => {

    const getFoldersResponse = {};
    const folderId = 1;
    let store;

    beforeEach(() => {
        store = configureMockStore([thunk])({});
    });

    afterEach(() => {
        fetchMock.reset();
        fetchMock.restore();
    });

    it('getFolders is successful', () => {

        fetchMock.getOnce('/api/folders', getFoldersResponse);

        return store.dispatch(getFolders()).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('getFolder is successful', () => {

        fetchMock.getOnce('/api/folders/' + folderId, getFoldersResponse);

        return store.dispatch(getFolder(folderId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('setFolder is successful', () => {

        const folder = {path: 'folderpath'};

        store.dispatch(setFolder(folder));
        expect(store.getActions()).toMatchSnapshot();
    });

    it('createFolder is successful', () => {

        const folder = {path: 'folderpath'};

        fetchMock.postOnce('/api/folders/create', folder);

        return store.dispatch(createFolder(folder)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('createFolder failed', () => {

        const folder = {path: 'folderpath'};

        return store.dispatch(createFolder(folder)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('deleteFolder is successful', () => {

        fetchMock.deleteOnce('/api/folders/' + folderId, {});

        return store.dispatch(deleteFolder(folderId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('deleteFolder failed', () => {

        return store.dispatch(deleteFolder(folderId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('setEncryptedFolderPassphrase is successful', () => {

        const passphrase = {passphrase: 'random'};

        store.dispatch(setEncryptedFolderPassphrase(passphrase));
        expect(store.getActions()).toMatchSnapshot();
    });

    it('confirmEncryptedFolder is successful', () => {

        const passphrase = 'random';
        const data = {
            folderId: folderId,
            passphrase: passphrase
        };

        fetchMock.postOnce('/api/folders/mount', data);

        return store.dispatch(confirmEncryptedFolder(folderId, passphrase)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('confirmEncryptedFolder failed', () => {

        const passphrase = 'random';

        return store.dispatch(confirmEncryptedFolder(folderId, passphrase)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

});