import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditGroup from "./EditGroup";

describe('EditGroup', () => {

    let props = {
        group: {},
        usersInGroup: [],
        availableUsers: []
    };

    it('renders without crashing', () => {
        expect(mount(<EditGroup {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<EditGroup {...props}/>))).toMatchSnapshot();
    });
});
