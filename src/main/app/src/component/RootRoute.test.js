import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import RootRoute from "./RootRoute";

describe('RootRoute', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<RootRoute/>))).toMatchSnapshot();
    });
});