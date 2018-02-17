import thunk from 'redux-thunk'
import configureMockStore from 'redux-mock-store'
import fetchMock from 'fetch-mock'
import {
    addUser,
    createGroup,
    deleteGroup,
    getGroup,
    getGroups,
    getUsersForGroup,
    removeUser,
    setGroup
} from "./groupAction";

describe('groupAction', () => {

    const getGroupsResponse = {};
    let groupId = 10;
    const userId = 100;
    const group = {groupName: 'name'};
    let store;

    beforeEach(() => {
        store = configureMockStore([thunk])({});
    });

    afterEach(() => {
        fetchMock.reset();
        fetchMock.restore();
    });

    it('getGroups is successful', () => {

        fetchMock.getOnce('/api/groups', getGroupsResponse);

        return store.dispatch(getGroups()).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('getGroup is successful', () => {

        fetchMock.getOnce('/api/groups/' + groupId, getGroupsResponse);

        return store.dispatch(getGroup(groupId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('setGroup is successful', () => {

        store.dispatch(setGroup(group));
        expect(store.getActions()).toMatchSnapshot();
    });

    it('createGroup is successful', () => {

        fetchMock.postOnce('/api/groups/add', group);

        return store.dispatch(createGroup(group)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('createGroup failed', () => {

        return store.dispatch(createGroup(group)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('deleteGroup is successful', () => {

        fetchMock.deleteOnce('/api/groups/' + groupId, {});

        return store.dispatch(deleteGroup(groupId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('deleteGroup failed', () => {

        return store.dispatch(deleteGroup(groupId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('getUsersForGroup is successful', () => {

        fetchMock.getOnce('/api/groups/' + groupId + '/users', getGroupsResponse);

        return store.dispatch(getUsersForGroup(groupId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('addUser is successful', () => {

        let data = {
            userId: userId,
            groupId: groupId
        };

        fetchMock.postOnce('/api/groups/user', data);
        fetchMock.getOnce('/api/groups/' + groupId + '/users', getGroupsResponse);

        return store.dispatch(addUser(groupId, userId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('addUser failed', () => {

        return store.dispatch(addUser(groupId, userId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('removeUser is successful', () => {

        fetchMock.deleteOnce('/api/groups/' + groupId + "/user/" + userId, {});
        fetchMock.getOnce('/api/groups/' + groupId + '/users', getGroupsResponse);

        return store.dispatch(removeUser(groupId, userId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('removeUser failed', () => {

        return store.dispatch(removeUser(groupId, userId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });
});