import React from 'react';
import {mount} from 'enzyme'
import {EditGroupContainer} from "./EditGroupContainer";

describe('EditGroupContainer', () => {

    it('renders without crashing', () => {
        let props = {
            group: {},
            match: {
                params: {
                    id: 0
                }
            },
            usersInGroup: [],
            users: [],
            getGroup: jest.fn(),
            getUsersForGroup: jest.fn(),
            getUsers: jest.fn(),
        };

        expect(mount(<EditGroupContainer {...props}/>).length).toEqual(1);
    });
});
