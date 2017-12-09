import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import UserList from "./UserList";

describe('UserList', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<UserList/>))).toMatchSnapshot();
    });
});
