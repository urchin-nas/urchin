import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Navigation from "./Navigation";

describe('Navigation', () => {

    it('renders without crashing', () => {
        expect(mount(<Navigation/>).length).toEqual(1);
    });


    it('match snapshot', () => {
        expect(toJson(shallow(<Navigation/>))).toMatchSnapshot();
    });
});
