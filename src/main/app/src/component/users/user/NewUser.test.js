import React from 'react';
import {mount} from 'enzyme'
import NewUser from "./NewUser";

describe('NewUser', () => {

    it('renders without crashing', () => {
        let props = {
            user: {},
        };

        expect(mount(<NewUser {...props}/>).length).toEqual(1);
    });
});
