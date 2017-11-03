import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import GroupList from "./GroupList";

describe('GroupList', () => {

    it('renders without crashing', () => {
        expect(mount(<GroupList/>).length).toEqual(1);
    });


    it('match snapshot', () => {
        expect(toJson(shallow(<GroupList/>))).toMatchSnapshot();
    });
});
