import React from 'react';
import {mount} from 'enzyme'
import EditUser from "./EditUser";

describe('EditUser', () => {

    it('renders without crashing', () => {
        let props = {
            user: {},
            groupsForUser: [],
            availableGroups: []
        };

        expect(mount(<EditUser {...props}/>).length).toEqual(1);
    });
});
