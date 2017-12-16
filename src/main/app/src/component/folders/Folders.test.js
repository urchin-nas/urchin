import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Folders from "./Folders";

describe('Folders', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<Folders/>))).toMatchSnapshot();
    });
});
