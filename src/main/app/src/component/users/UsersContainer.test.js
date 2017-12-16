import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {UsersContainer} from "./UsersContainer";

describe('UsersContainer', () => {

    let props = {
        getUsers: jest.fn()
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<UsersContainer {...props}/>))).toMatchSnapshot();
    });
});
