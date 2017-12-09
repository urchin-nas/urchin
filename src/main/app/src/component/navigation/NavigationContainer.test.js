import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {NavigationContainer} from "./NavigationContainer";

describe('NewGroupContainer', () => {

    it('renders without crashing', () => {

        expect(mount(<NavigationContainer/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NavigationContainer/>))).toMatchSnapshot();
    });
});
