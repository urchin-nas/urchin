import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {GroupsContainer} from "./GroupsContainer";

describe('GroupsContainer', () => {

    let props = {
        getGroups: jest.fn(),
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<GroupsContainer {...props}/>))).toMatchSnapshot();
    });
});
