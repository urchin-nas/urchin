import reducer from './groupReducer';
import {Actions} from "../constants";

describe('groupReducer', () => {

    const group = {
        groupId: 10,
        groupName: 'group'
    };

    it('initial state', () => {

        expect(reducer(undefined, {})).toMatchSnapshot();
    });

    it('GET_GROUPS', () => {

        expect(reducer({}, {type: Actions.Groups.GET_GROUPS})).toMatchSnapshot();
    });

    it('GET_GROUPS_SUCCESS', () => {

        const action = {
            type: Actions.Groups.GET_GROUPS_SUCCESS,
            data: [group]
        };

        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('GET_GROUP', () => {

        expect(reducer({}, {type: Actions.Group.GET_GROUP})).toMatchSnapshot();
    });

    it('GET_GROUP_SUCCESS', () => {

        const action = {
            type: Actions.Group.GET_GROUP_SUCCESS,
            data: group
        };

        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SET_GROUP', () => {

        const action = {
            type: Actions.Group.SET_GROUP,
            data: {
                field: 'groupName',
                value: 'group'
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SAVE_GROUP', () => {

        expect(reducer({}, {type: Actions.Group.SAVE_GROUP})).toMatchSnapshot();
    });


    it('SAVE_GROUP_SUCCESS', () => {

        const action = {
            type: Actions.Group.SAVE_GROUP_SUCCESS,
            data: {
                id: 10
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SAVE_GROUP_VALIDATION_ERROR', () => {

        const action = {
            type: Actions.Group.SAVE_GROUP_VALIDATION_ERROR,
            data: {
                errorCode: 'VALIDATION_ERROR',
                message: 'validation error',
                fieldErrors: {
                    groupName: [
                        'size must be between 3 and 32'
                    ]
                }
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });
    it('GET_USERS_FOR_GROUP', () => {

        expect(reducer({}, {type: Actions.Group.GET_USERS_FOR_GROUP})).toMatchSnapshot();
    });

    it('GET_USERS_FOR_GROUP_SUCCESS', () => {

        const action = {
            type: Actions.Group.GET_USERS_FOR_GROUP_SUCCESS,
            data: [
                {
                    userId: 100,
                    username: 'user'
                }
            ]
        };
        expect(reducer({}, action)).toMatchSnapshot();
    });
});
