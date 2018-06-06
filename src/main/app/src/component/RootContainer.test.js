import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {RootContainer} from "./RootContainer";
import {Actions} from "../constants";

describe('RootContainer', () => {

    it('match snapshot when not authenticated', () => {
        const props = {
            isAuthenticated: jest.fn(),
        };

        expect(toJson(shallow(<RootContainer {...props}/>))).toMatchSnapshot();
    });

    it('match snapshot when logged in', () => {
        const props = {
            isAuthenticated: jest.fn(),
            authed: Actions.Auth.LOGIN_SUCCESS
        };

        expect(toJson(shallow(<RootContainer {...props}/>))).toMatchSnapshot();
    });

    it('match snapshot when login invalid', () => {
        const props = {
            isAuthenticated: jest.fn(),
            authed: Actions.Auth.LOGIN_INVALID
        };

        expect(toJson(shallow(<RootContainer {...props}/>))).toMatchSnapshot();
    });

    it('match snapshot when login failed', () => {
        const props = {
            isAuthenticated: jest.fn(),
            authed: Actions.Auth.LOGIN_FAILED
        };

        expect(toJson(shallow(<RootContainer {...props}/>))).toMatchSnapshot();
    });

    it('match snapshot when precondition failed', () => {
        const props = {
            isAuthenticated: jest.fn(),
            authed: Actions.Auth.PRECONDITION_FAILED
        };

        expect(toJson(shallow(<RootContainer {...props}/>))).toMatchSnapshot();
    });
});
