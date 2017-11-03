import React from 'react';
import {mount} from 'enzyme'
import LoginContainer from "./LoginContainer";

describe("LoginContainer", () => {

    it('renders without crashing', () => {
        expect(mount(<LoginContainer/>).length).toEqual(1);
    });
});
