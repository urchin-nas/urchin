import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import LoginContainer from "./LoginContainer";

describe("LoginContainer", () => {

    it('renders without crashing', () => {
        expect(mount(<LoginContainer/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<LoginContainer/>))).toMatchSnapshot();
    });
});
