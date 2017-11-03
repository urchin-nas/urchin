import React from 'react';
import {mount} from 'enzyme'
import GroupList from "./GroupList";

describe('GroupList', () => {

    it('renders without crashing', () => {
        expect(mount(<GroupList/>).length).toEqual(1);
    });
});
