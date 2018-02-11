import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {ConfirmNewFolderContainer} from "./ConfirmNewFolderContainer";

describe('ConfirmNewFolderContainer', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<ConfirmNewFolderContainer/>))).toMatchSnapshot();
    });
});
