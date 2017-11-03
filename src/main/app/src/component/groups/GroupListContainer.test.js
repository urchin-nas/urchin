import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {GroupListContainer} from "./GroupListContainer";

describe('GroupListContainer', () => {

    let props = {
        getGroups: jest.fn(),
    };

    it('renders without crashing', () => {
        expect(mount(<GroupListContainer {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<GroupListContainer {...props}/>))).toMatchSnapshot();
    });
});
