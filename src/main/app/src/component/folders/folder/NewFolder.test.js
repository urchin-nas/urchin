import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewFolder from "./NewFolder";

describe('NewFolder', () => {

    let props = {
        folder: {},
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<NewFolder {...props}/>))).toMatchSnapshot();
    });
});
