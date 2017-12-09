import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {NewUserContainer} from "./NewUserContainer";

describe('NewUserContainer', () => {

    let props = {
        user: {},
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<NewUserContainer {...props}/>))).toMatchSnapshot();
    });
});
