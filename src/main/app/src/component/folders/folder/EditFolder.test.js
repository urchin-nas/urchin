import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditFolder from "./EditFolder";

describe('EditFolder', () => {

    let props = {
        folder: {},
        usersInFolder: [],
        availableUsers: []
    };

    it('renders without crashing', () => {
        expect(mount(<EditFolder {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<EditFolder {...props}/>))).toMatchSnapshot();
    });
});
