import thunk from 'redux-thunk'
import configureMockStore from 'redux-mock-store'
import fetchMock from 'fetch-mock'
import {setAdmin, setupAdmin} from "./adminAction";

describe('authAction', () => {

    const admin = {username: 'admin'};

    let store;

    beforeEach(() => {
        store = configureMockStore([thunk])({});
    });

    afterEach(() => {
        fetchMock.reset();
        fetchMock.restore();
    });

    it('setAdmin is successful', () => {

        store.dispatch(setAdmin(admin));
        expect(store.getActions()).toMatchSnapshot();
    });

    it('setupAdmin is successful', () => {

        fetchMock.postOnce('/api/authenticate/add-first-admin', admin);

        return store.dispatch(setupAdmin(admin)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('setupAdmin failed', () => {

        return store.dispatch(setupAdmin(admin)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

});