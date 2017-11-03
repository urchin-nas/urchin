import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import FieldError from "./FieldError";

describe('FieldError', () => {

    it('renders without crashing', () => {
        expect(mount(<FieldError/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<FieldError/>))).toMatchSnapshot();
    });
});
