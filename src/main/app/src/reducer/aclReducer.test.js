import reducer from './aclReducer';
import {Actions} from "../constants";

describe('aclReducer', () => {

    it('should return the initial state', () => {
        expect(reducer(undefined, {})).toEqual({})
    });

    it('GET_ACL', () => {
        expect(reducer({}, {type: Actions.ACL.GET_ACL})).toMatchSnapshot();
    });

    it('GET_ACL_SUCCESS', () => {
        let action = {
            type: Actions.ACL.GET_ACL_SUCCESS,
            data: {
                groups: [
                    {
                        group: {
                            groupId: 10,
                            groupName: 'group'
                        },
                        aclPermissions: {
                            hasRead: true,
                            hasWrite: true,
                            hasExecute: true
                        }
                    }
                ],
                users: [
                    {
                        user: {
                            userId: 100,
                            username: 'user'
                        },
                        aclPermissions: {
                            hasRead: true,
                            hasWrite: true,
                            hasExecute: true
                        }
                    }
                ]
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    })
});
