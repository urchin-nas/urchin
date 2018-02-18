import reducer from './siteReducer';
import {Actions} from "../constants";

describe('siteReducer', () => {

    it('initial state', () => {

        expect(reducer(undefined, {})).toMatchSnapshot();
    });

    it('TOGGLE_NAVIGATION', () => {

        const action = {
            type: Actions.Site.TOGGLE_NAVIGATION,
            data: true
        };

        expect(reducer({}, action)).toMatchSnapshot();
    });

});
