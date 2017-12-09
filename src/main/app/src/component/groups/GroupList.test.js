import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import GroupList from "./GroupList";

describe('GroupList', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<GroupList/>))).toMatchSnapshot();
    });
});
