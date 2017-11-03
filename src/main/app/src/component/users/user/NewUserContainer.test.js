import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {NewUserContainer} from "./NewUserContainer";

describe('NewUserContainer', () => {

    let props = {
        user: {},
    };

    it('renders without crashing', () => {
        expect(mount(<NewUserContainer {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NewUserContainer {...props}/>))).toMatchSnapshot();
    });
});
