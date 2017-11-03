import React from 'react';
import {mount} from 'enzyme'
import MainMenu from "./MainMenu";

describe('MainMenu', () => {

    it('renders without crashing', () => {
        expect(mount(<MainMenu/>).length).toEqual(1);
    });
});
