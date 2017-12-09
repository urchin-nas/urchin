import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewUser from "./NewUser";

describe('NewUser', () => {

    let props = {
        user: {},
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<NewUser {...props}/>))).toMatchSnapshot();
    });
});
