import React from 'react';
import {Router} from 'react-router-dom'
import {mount} from 'enzyme'
import MainContainer from "./MainContainer";
import history from '../history'

describe('MainContainer', () => {

    it('renders without crashing', () => {
        let component = mount(
            <Router history={history}>
                <MainContainer/>
            </Router>
        );

        expect(component.length).toEqual(1);
    });
});