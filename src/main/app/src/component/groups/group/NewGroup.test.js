import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewGroup from "./NewGroup";

describe('NewGroup', () => {

    let props = {
        group: {},
    };

    it('renders without crashing', () => {
        expect(mount(<NewGroup {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NewGroup {...props}/>))).toMatchSnapshot();
    });
});
