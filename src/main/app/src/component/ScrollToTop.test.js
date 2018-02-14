import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import ScrollToTop from "./ScrollToTop";

describe('ScrollToTop', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<ScrollToTop/>))).toMatchSnapshot();
    });
});