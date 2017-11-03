import React from 'react';
import {mount} from 'enzyme'
import {EditGroupContainer} from "./EditGroupContainer";
import {NewGroupContainer} from "./NewGroupContainer";

describe('EditGroupContainer', () => {

    it('renders without crashing', () => {
        let props = {
            group: {},
        };

        expect(mount(<NewGroupContainer {...props}/>).length).toEqual(1);
    });
});
