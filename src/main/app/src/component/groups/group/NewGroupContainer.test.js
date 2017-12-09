import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {NewGroupContainer} from "./NewGroupContainer";

describe('NewGroupContainer', () => {

    let props = {
        group: {},
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<NewGroupContainer {...props}/>))).toMatchSnapshot();
    });
});
