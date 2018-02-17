import thunk from 'redux-thunk'
import configureMockStore from 'redux-mock-store'
import fetchMock from 'fetch-mock'
import {getAcl, updateAclForGroup, updateAclForUser} from "./aclAction";

describe('AclAction', () => {

    const folderId = 1;
    const getAclResponse = {
        groups: [],
        users: []
    };

    let store;

    beforeEach(() => {
        store = configureMockStore([thunk])({});

        fetchMock.get('/api/permissions/acl/' + folderId, getAclResponse);
    });

    afterEach(() => {
        fetchMock.reset();
        fetchMock.restore();
    });

    it('getAcl is successful', () => {

        return store.dispatch(getAcl(folderId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('updateAclForGroup is successful', () => {

        const groupAcl = {
            folderId: folderId
        };

        fetchMock.postOnce('/api/permissions/acl/group', groupAcl);

        return store.dispatch(updateAclForGroup(groupAcl)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('updateAclForUser is successful', () => {

        const userAcl = {
            folderId: folderId,
        };

        fetchMock.postOnce('/api/permissions/acl/user', userAcl);

        return store.dispatch(updateAclForUser(userAcl)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });
});