import React from 'react';
import {mount} from 'enzyme'
import UserList from "./UserList";

describe('UserList', () => {

    it('renders without crashing', () => {
        expect(mount(<UserList/>).length).toEqual(1);
    });
});
