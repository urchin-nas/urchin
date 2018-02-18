import reducer from './rootReducer';

describe('rootReducer', () => {

    it('initial state', () => {

        expect(reducer(undefined, {})).toMatchSnapshot();
    });

});
