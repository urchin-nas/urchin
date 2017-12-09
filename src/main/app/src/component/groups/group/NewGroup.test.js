import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewGroup from "./NewGroup";

describe('NewGroup', () => {

    let props = {
        group: {},
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<NewGroup {...props}/>))).toMatchSnapshot();
    });
});
