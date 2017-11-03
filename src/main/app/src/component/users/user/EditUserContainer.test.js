import React from 'react';
import {mount, shallow} from 'enzyme'
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

    it('renders without crashing', () => {
        expect(mount(<EditUserContainer {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<EditUserContainer {...props}/>))).toMatchSnapshot();
    });
});
