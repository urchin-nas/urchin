import reducer from './adminReducer';
import {Actions} from "../constants";

describe('adminReducer', () => {

    it('initial state', () => {

        expect(reducer(undefined, {})).toMatchSnapshot()
    });

    it('SET_ADMIN', () => {
        const action = {
            type: Actions.Admin.SET_ADMIN,
            data: {
                field: 'username',
                value: 'admin'
            }
        };

        expect(reducer({}, action)).toMatchSnapshot();
    });

    it('SETUP_ADMIN', () => {

        expect(reducer({}, {type: Actions.Admin.SETUP_ADMIN})).toMatchSnapshot();
    });

    it('SETUP_ADMIN_SUCCESS', () => {

        expect(reducer({}, {type: Actions.Admin.SETUP_ADMIN_SUCCESS})).toMatchSnapshot();
    });

    it('SETUP_ADMIN_VALIDATION_ERROR', () => {

        expect(reducer({}, {type: Actions.Admin.SETUP_ADMIN_VALIDATION_ERROR})).toMatchSnapshot();
    });

});
