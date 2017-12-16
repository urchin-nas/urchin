import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Users from "./Users";

describe('Users', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<Users/>))).toMatchSnapshot();
    });
});
