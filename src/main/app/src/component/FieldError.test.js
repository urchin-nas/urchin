import React from 'react';
import {mount} from 'enzyme'
import FieldError from "./FieldError";

describe('FieldError', () => {

    it('renders without crashing', () => {
        expect(mount(<FieldError/>).length).toEqual(1);
    });
});
