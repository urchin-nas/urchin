import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {EditUserContainer} from "./EditUserContainer";

describe('EditUserContainer', () => {

    let props = {
        match: {
            params: {
                id: 0
            }
        },
        user: {},
        groupsForUser: [],
        groups: [],
        getUser: jest.fn(),
        getGroupsForUser: jest.fn(),
        getGroups: jest.fn(),
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<EditUserContainer {...props}/>))).toMatchSnapshot();
    });
});
