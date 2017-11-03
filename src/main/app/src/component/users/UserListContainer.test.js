import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {UserListContainer} from "./UserListContainer";

describe('UserListContainer', () => {

    let props = {
        getUsers: jest.fn()
    };

    it('renders without crashing', () => {
        expect(mount(<UserListContainer {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<UserListContainer {...props}/>))).toMatchSnapshot();
    });
});
