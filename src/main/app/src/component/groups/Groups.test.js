import React from 'react';
import {mount} from 'enzyme'
import {Router} from "react-router-dom";
import Groups from "./Groups";
import history from '../../history'

describe('Groups', () => {

    it('renders without crashing', () => {
        let component = mount(
            <Router history={history}>
                <Groups/>
            </Router>
        );
        expect(component.length).toEqual(1);
    });
});
