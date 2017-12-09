import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {EditGroupContainer} from "./EditGroupContainer";

describe('EditGroupContainer', () => {

    let props = {
        match: {
            params: {
                id: 0
            }
        },
        group: {},
        usersInGroup: [],
        users: [],
        getGroup: jest.fn(),
        getUsersForGroup: jest.fn(),
        getUsers: jest.fn(),
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<EditGroupContainer {...props}/>))).toMatchSnapshot();
    });
});
