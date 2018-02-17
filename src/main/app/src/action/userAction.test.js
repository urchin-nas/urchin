import thunk from 'redux-thunk'
import configureMockStore from 'redux-mock-store'
import fetchMock from 'fetch-mock'
import {
    addGroup,
    createUser,
    deleteUser,
    getGroupsForUser,
    getUser,
    getUsers,
    removeGroup,
    setUser
} from "./userAction";

describe('userAction', () => {

    const getUsersResponse = {};
    const folderId = 1;
    const groupId = 10;
    const userId = 100;
    const user = {username: 'user'};
    let store;

    beforeEach(() => {
        store = configureMockStore([thunk])({});
    });

    afterEach(() => {
        fetchMock.reset();
        fetchMock.restore();
    });

    it('getUsers is successful', () => {

        fetchMock.getOnce('/api/users', getUsersResponse);

        return store.dispatch(getUsers()).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('getUser is successful', () => {

        fetchMock.getOnce('/api/users/' + userId, getUsersResponse);

        return store.dispatch(getUser(userId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('setUser is successful', () => {

        store.dispatch(setUser(user));
        expect(store.getActions()).toMatchSnapshot();
    });

    it('createUser is successful', () => {

        fetchMock.postOnce('/api/users/add', user);

        return store.dispatch(createUser(user)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('createUser failed', () => {

        return store.dispatch(createUser(user)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('deleteUser is successful', () => {

        fetchMock.deleteOnce('/api/users/' + userId, {});

        return store.dispatch(deleteUser(userId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('deleteUser failed', () => {

        return store.dispatch(deleteUser(userId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('getGroupsForUser is successful', () => {

        const getGroupsResponse = {};

        fetchMock.getOnce('/api/users/' + userId + "/groups", getGroupsResponse);

        store.dispatch(getGroupsForUser(userId));
        expect(store.getActions()).toMatchSnapshot();
    });

    it('addGroup is successful', () => {

        const getGroupsResponse = {};
        const data = {
            userId: userId,
            groupId
        };

        fetchMock.postOnce('/api/groups/user', data);
        fetchMock.getOnce('/api/users/' + userId + "/groups", getGroupsResponse);

        return store.dispatch(addGroup(userId, groupId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('addGroup failed', () => {

        return store.dispatch(addGroup(userId, groupId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('removeGroup is successful', () => {

        const getGroupsResponse = {};
        fetchMock.deleteOnce('/api/groups/' + groupId + "/user/" + userId, {});
        fetchMock.getOnce('/api/users/' + userId + "/groups", getGroupsResponse)

        return store.dispatch(removeGroup(userId, groupId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('removeGroup failed', () => {

        return store.dispatch(removeGroup(userId, groupId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

});