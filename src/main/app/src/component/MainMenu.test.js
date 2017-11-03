import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import MainMenu from "./MainMenu";

describe('MainMenu', () => {

    it('renders without crashing', () => {
        expect(mount(<MainMenu/>).length).toEqual(1);
    });


    it('match snapshot', () => {
        expect(toJson(shallow(<MainMenu/>))).toMatchSnapshot();
    });
});
