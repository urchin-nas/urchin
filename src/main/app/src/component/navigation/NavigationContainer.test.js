import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {NavigationContainer} from "./NavigationContainer";

describe('NewGroupContainer', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<NavigationContainer/>))).toMatchSnapshot();
    });
});
