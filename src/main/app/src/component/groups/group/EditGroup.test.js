import React from 'react';
import {mount} from 'enzyme'
import EditGroup from "./EditGroup";

describe('EditGroup', () => {

    it('renders without crashing', () => {
        let props = {
            group: {},
            usersInGroup: [],
            availableUsers: []
        };

        expect(mount(<EditGroup {...props}/>).length).toEqual(1);
    });
});
