import reducer from './aclReducer';
import {Actions} from "../constants";

describe('aclReducer', () => {

    const group = {
        group: {
            groupId: 10,
            groupName: 'group'
        },
        aclPermissions: {
            hasRead: true,
            hasWrite: true,
            hasExecute: true
        }
    };

    const user = {
        user: {
            userId: 100,
            username: 'user'
        },
        aclPermissions: {
            hasRead: true,
            hasWrite: true,
            hasExecute: true
        }
    };

    it('initial state', () => {

        expect(reducer(undefined, {})).toMatchSnapshot()
    });

    it('GET_ACL', () => {

        expect(reducer({}, {type: Actions.ACL.GET_ACL})).toMatchSnapshot();
    });

    it('GET_ACL_SUCCESS', () => {

        const action = {
            type: Actions.ACL.GET_ACL_SUCCESS,
            data: {
                groups: [group],
                users: [user]
            }
        };
        expect(reducer({}, action)).toMatchSnapshot();
    })
});
