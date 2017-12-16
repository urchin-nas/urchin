import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Groups from "./Groups";

describe('Groups', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<Groups/>))).toMatchSnapshot();
    });
});
