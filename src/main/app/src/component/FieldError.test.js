import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import FieldError from "./FieldError";

describe('FieldError', () => {

    it('match snapshot without error to display', () => {
        expect(toJson(shallow(<FieldError/>))).toMatchSnapshot();
    });

    it('match snapshot with error to display', () => {

        let props = {
            field: 'someField',
            fieldErrors: {
                someField: [
                    'error message',
                    'some other error message'
                ],
                someOtherField: [
                    'error message that should not be displayed'
                ]
            }
        };

        expect(toJson(shallow(<FieldError {...props}/>))).toMatchSnapshot();
    });
});
