import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditGroup from "./EditGroup";

describe('EditGroup', () => {

    let props = {
        group: {},
        usersInGroup: [],
        availableUsers: []
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<EditGroup {...props}/>))).toMatchSnapshot();
    });
});
