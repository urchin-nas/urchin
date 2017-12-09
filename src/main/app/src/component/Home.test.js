import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Home from "./Home";

describe('Home', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<Home/>))).toMatchSnapshot();
    });
});
