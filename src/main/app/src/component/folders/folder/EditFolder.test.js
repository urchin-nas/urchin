import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditFolder from "./EditFolder";

describe('EditFolder', () => {

    let props = {
        folder: {},
        usersInFolder: [],
        availableUsers: []
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<EditFolder {...props}/>))).toMatchSnapshot();
    });
});
