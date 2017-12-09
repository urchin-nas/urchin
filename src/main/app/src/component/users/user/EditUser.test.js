import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditUser from "./EditUser";

describe('EditUser', () => {

    let props = {
        user: {},
        groupsForUser: [],
        availableGroups: []
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<EditUser {...props}/>))).toMatchSnapshot();
    });
});
