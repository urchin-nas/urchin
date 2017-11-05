import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewFolder from "./NewFolder";

describe('NewFolder', () => {

    let props = {
        folder: {},
    };

    it('renders without crashing', () => {
        expect(mount(<NewFolder {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NewFolder {...props}/>))).toMatchSnapshot();
    });
});
