import React from 'react';
import {mount} from 'enzyme'
import {Router} from "react-router-dom";
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
});
