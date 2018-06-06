import reducer from './authReducer';
import {Actions} from "../constants";

describe('authReducer', () => {

    it('initial state', () => {

        expect(reducer(undefined, {})).toMatchSnapshot()
    });

    it('LOGIN', () => {

        expect(reducer({}, {type: Actions.Auth.LOGIN})).toMatchSnapshot();
    });

    it('LOGIN_SUCCESS', () => {

        const action = {
            type: Actions.Auth.LOGIN_SUCCESS,
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('LOGIN_INVALID', () => {

        const action = {
            type: Actions.Auth.LOGIN_INVALID,
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('LOGIN_FAILED', () => {

        const action = {
            type: Actions.Auth.LOGIN_FAILED,
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('PRECONDITION_FAILED', () => {

        const action = {
            type: Actions.Auth.PRECONDITION_FAILED,
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('LOGOUT_SUCCESS', () => {

        const action = {
            type: Actions.Auth.LOGOUT_SUCCESS,
        };
        expect(reducer({}, action)).toMatchSnapshot();
    })
});
