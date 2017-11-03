import React from 'react';
import {mount} from 'enzyme'
import {EditGroupContainer} from "./EditGroupContainer";

describe('EditGroupContainer', () => {

    it('renders without crashing', () => {
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

        expect(mount(<EditGroupContainer {...props}/>).length).toEqual(1);
    });
});
