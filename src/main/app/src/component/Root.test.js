import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Root from "./Root";

describe('Root', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<Root/>))).toMatchSnapshot();
    });
});