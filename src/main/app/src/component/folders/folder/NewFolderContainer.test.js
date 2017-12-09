import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {NewFolderContainer} from "./NewFolderContainer";

describe('NewFolderContainer', () => {

    let props = {
        folder: {},
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<NewFolderContainer {...props}/>))).toMatchSnapshot();
    });
});
