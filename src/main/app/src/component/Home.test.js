import React from 'react';
import {mount} from 'enzyme'
import Home from "./Home";

describe('Home', () => {

    it('renders without crashing', () => {
        expect(mount(<Home/>).length).toEqual(1);
    });
});
