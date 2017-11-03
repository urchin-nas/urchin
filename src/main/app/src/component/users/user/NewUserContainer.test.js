import React from 'react';
import {mount} from 'enzyme'
import {NewUserContainer} from "./NewUserContainer";

describe('NewUserContainer', () => {

    it('renders without crashing', () => {
        let props = {
            user: {},
        };

        expect(mount(<NewUserContainer {...props}/>).length).toEqual(1);
    });
});
