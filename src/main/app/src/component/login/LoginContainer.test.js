import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import LoginContainer from "./LoginContainer";

describe("LoginContainer", () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<LoginContainer/>))).toMatchSnapshot();
    });
});
