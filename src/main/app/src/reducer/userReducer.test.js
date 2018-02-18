import reducer from './userReducer';
import {Actions} from "../constants";

describe('userReducer', () => {

    const user = {
        userId: 100,
        username: 'user'
    };

    it('initial state', () => {

        expect(reducer(undefined, {})).toMatchSnapshot();
    });

    it('GET_USERS', () => {

        expect(reducer({}, {type: Actions.Users.GET_USERS})).toMatchSnapshot();
    });

    it('GET_USERS_SUCCESS', () => {

        const action = {
            type: Actions.Users.GET_USERS_SUCCESS,
            data: [user]
        };

        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('GET_USER', () => {

        expect(reducer({}, {type: Actions.User.GET_USER})).toMatchSnapshot();
    });

    it('GET_USER_SUCCESS', () => {

        const action = {
            type: Actions.User.GET_USER_SUCCESS,
            data: user
        };

        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SET_USER', () => {

        const action = {
            type: Actions.User.SET_USER,
            data: {
                field: 'username',
                value: 'user'
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SAVE_USER', () => {

        expect(reducer({}, {type: Actions.User.SAVE_USER})).toMatchSnapshot();
    });

    it('SAVE_USER_SUCCESS', () => {

        const action = {
            type: Actions.User.SAVE_USER_SUCCESS,
            data: {
                id: 100,
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SAVE_USER_VALIDATION_ERROR', () => {

        const action = {
            type: Actions.User.SAVE_USER_VALIDATION_ERROR,
            data: {
                errorCode: 'VALIDATION_ERROR',
                message: 'validation error',
                fieldErrors: {
                    password: [
                        'size must be between 6 and 2147483647'
                    ],
                    username: [
                        'size must be between 3 and 32'
                    ]
                }
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('GET_GROUPS_FOR_USER', () => {

        expect(reducer({}, {type: Actions.User.GET_GROUPS_FOR_USER})).toMatchSnapshot();
    });

    it('GET_GROUPS_FOR_USER_SUCCESS', () => {

        const action = {
            type: Actions.User.GET_GROUPS_FOR_USER_SUCCESS,
            data: [
                {
                    groupId: 10,
                    groupName: 'group'
                }
            ]
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });
});
