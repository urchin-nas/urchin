import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import FoldersRoute from "./FoldersRoute";

describe('FoldersRoute', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<FoldersRoute/>))).toMatchSnapshot();
    });
});
