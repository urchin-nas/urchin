import React from 'react';
import {mount} from 'enzyme'
import NewGroup from "./NewGroup";

describe('NewGroup', () => {

    it('renders without crashing', () => {
        let props = {
            group: {},
        };

        expect(mount(<NewGroup {...props}/>).length).toEqual(1);
    });
});
