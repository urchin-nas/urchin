import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Home from "./Home";

describe('Home', () => {

    it('renders without crashing', () => {
        expect(mount(<Home/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<Home/>))).toMatchSnapshot();
    });
});
