import React from 'react';
import {mount} from 'enzyme'
import {EditUserContainer} from "./EditUserContainer";

describe('EditUserContainer', () => {

    it('renders without crashing', () => {
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

        expect(mount(<EditUserContainer {...props}/>).length).toEqual(1);
    });
});
