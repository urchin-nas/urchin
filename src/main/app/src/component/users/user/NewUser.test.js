import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewUser from "./NewUser";

describe('NewUser', () => {

    let props = {
        user: {},
    };

    it('renders without crashing', () => {
        expect(mount(<NewUser {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NewUser {...props}/>))).toMatchSnapshot();
    });
});
