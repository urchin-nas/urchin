import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {GroupListContainer} from "./GroupListContainer";

describe('GroupListContainer', () => {

    let props = {
        getGroups: jest.fn(),
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<GroupListContainer {...props}/>))).toMatchSnapshot();
    });
});
