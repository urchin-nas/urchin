import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {UserListContainer} from "./UserListContainer";

describe('UserListContainer', () => {

    let props = {
        getUsers: jest.fn()
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<UserListContainer {...props}/>))).toMatchSnapshot();
    });
});
