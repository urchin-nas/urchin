import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditUser from "./EditUser";

describe('EditUser', () => {

    let props = {
        user: {},
        groupsForUser: [],
        availableGroups: []
    };

    it('renders without crashing', () => {
        expect(mount(<EditUser {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<EditUser {...props}/>))).toMatchSnapshot();
    });
});
