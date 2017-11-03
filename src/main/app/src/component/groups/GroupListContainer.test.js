import React from 'react';
import {mount} from 'enzyme'
import {GroupListContainer} from "./GroupListContainer";

describe('GroupListContainer', () => {

    let getGroups = jest.fn();

    it('renders without crashing', () => {
        expect(mount(<GroupListContainer getGroups={getGroups}/>).length).toEqual(1);
    });
});
