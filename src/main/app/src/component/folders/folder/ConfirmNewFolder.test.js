import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import ConfirmNewFolder from "./ConfirmNewFolder";

describe('ConfirmNewFolder', () => {

    let props = {
        confirmNewFolder: {},
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<ConfirmNewFolder {...props}/>))).toMatchSnapshot();
    });
});
