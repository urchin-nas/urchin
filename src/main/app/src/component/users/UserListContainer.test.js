import React from 'react';
import {mount} from 'enzyme'
import {UserListContainer} from "./UserListContainer";

describe('UserListContainer', () => {

    let getUsers = jest.fn();

    it('renders without crashing', () => {
        expect(mount(<UserListContainer getUsers={getUsers}/>).length).toEqual(1);
    });
});
