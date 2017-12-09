import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Navigation from "./Navigation";

describe('Navigation', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<Navigation/>))).toMatchSnapshot();
    });
});
