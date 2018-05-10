import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import LoginContainer from "./LoginContainer";
import Login from "./Login";

describe("LoginContainer", () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<Login/>))).toMatchSnapshot();
    });
});
