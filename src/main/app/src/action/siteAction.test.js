import thunk from 'redux-thunk'
import configureMockStore from 'redux-mock-store'
import fetchMock from 'fetch-mock'
import {toggleNavigation} from "./siteAction";

describe('userAction', () => {

    let store;

    beforeEach(() => {
        store = configureMockStore([thunk])({});
    });

    afterEach(() => {
        fetchMock.reset();
        fetchMock.restore();
    });

    it('toggleNavigation is successful', () => {

        store.dispatch(toggleNavigation(false));
        expect(store.getActions()).toMatchSnapshot();
    });

});