import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import FieldError from "./FieldError";

describe('FieldError', () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<FieldError/>))).toMatchSnapshot();
    });
});
