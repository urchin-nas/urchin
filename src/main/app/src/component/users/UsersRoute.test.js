import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import UsersRoute from "./UsersRoute";

describe('UsersRoute', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<UsersRoute/>))).toMatchSnapshot();
    });
});
