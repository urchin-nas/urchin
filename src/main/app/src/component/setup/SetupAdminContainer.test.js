import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {SetupAdminContainer} from "./SetupAdminContainer";

describe("LoginContainer", () => {

    it('match snapshot', () => {
        expect(toJson(shallow(<SetupAdminContainer/>))).toMatchSnapshot();
    });
});
