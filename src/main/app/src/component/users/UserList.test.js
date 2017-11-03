import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import UserList from "./UserList";

describe('UserList', () => {

    it('renders without crashing', () => {
        expect(mount(<UserList/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<UserList/>))).toMatchSnapshot();
    });
});
