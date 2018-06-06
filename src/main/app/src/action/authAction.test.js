import thunk from 'redux-thunk'
import configureMockStore from 'redux-mock-store'
import fetchMock from 'fetch-mock'
import {isAuthenticated, login} from "./authAction";

describe('authAction', () => {

    const username = 'username';
    const password = 'password';

    let store;

    beforeEach(() => {
        store = configureMockStore([thunk])({});
    });

    afterEach(() => {
        fetchMock.reset();
        fetchMock.restore();
    });

    it('authenticate is successful', () => {
        fetchMock.getOnce('/api/authenticate', {
            message: 'ok'
        });

        return store.dispatch(isAuthenticated()).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('authenticate failed', () => {
        return store.dispatch(isAuthenticated()).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('authenticate precondition failed', () => {
        fetchMock.getOnce('/api/authenticate', {
            status: 428,
            body: {}
        });

        return store.dispatch(isAuthenticated()).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('login is successful', () => {
        fetchMock.postOnce('/login', 200);

        return store.dispatch(login(username, password)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('login failed', () => {
        fetchMock.postOnce('/login', 500);

        return store.dispatch(login(username, password)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('login failed network error', () => {
        return store.dispatch(login(username, password)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

    it('login invalid', () => {
        fetchMock.postOnce('/login', 400);

        return store.dispatch(login(username, password)).then(() => {
            expect(store.getActions()).toMatchSnapshot();
        })
    });

});