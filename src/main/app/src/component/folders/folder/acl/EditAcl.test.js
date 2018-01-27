import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditAcl from "./EditAcl";

describe('EditAcl', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<EditAcl/>))).toMatchSnapshot();
    });
});
