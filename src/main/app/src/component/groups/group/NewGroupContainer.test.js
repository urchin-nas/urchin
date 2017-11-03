import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {NewGroupContainer} from "./NewGroupContainer";

describe('NewGroupContainer', () => {

    let props = {
        group: {},
    };

    it('renders without crashing', () => {

        expect(mount(<NewGroupContainer {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NewGroupContainer {...props}/>))).toMatchSnapshot();
    });
});
