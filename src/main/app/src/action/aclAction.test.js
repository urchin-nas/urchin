import thunk from 'redux-thunk'
import configureMockStore from 'redux-mock-store'
import fetchMock from 'fetch-mock'
import {getAcl} from "./aclAction";

describe('AclAction', () => {

    const arbitraryResponse = {data: 'some data'};
    const folderId = 1;

    let store;

    beforeEach(() => {
        store = configureMockStore([thunk])({});
    });

    afterEach(() => {
        fetchMock.reset();
        fetchMock.restore();
    });

    it('getAcl is successful', () => {
        fetchMock.getOnce('/api/permissions/acl/' + folderId, {
            body: arbitraryResponse,
            headers: {'content-type': 'application/json'}
        });


        return store.dispatch(getAcl(folderId)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });
});