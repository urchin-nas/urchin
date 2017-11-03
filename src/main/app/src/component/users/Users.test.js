import React from 'react';
import {mount, shallow} from 'enzyme'
import {Router} from "react-router-dom";
import toJson from 'enzyme-to-json';
import history from '../../history'
import Users from "./Users";

describe('Users', () => {

    it('renders without crashing', () => {
        let component = mount(
            <Router history={history}>
                <Users/>
            </Router>
        );
        expect(component.length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<Users/>))).toMatchSnapshot();
    });
});
