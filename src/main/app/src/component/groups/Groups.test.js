import React from 'react';
import {mount, shallow} from 'enzyme'
import {Router} from "react-router-dom";
import toJson from 'enzyme-to-json';
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

    it('match snapshot', () => {
        expect(toJson(shallow(<Groups/>))).toMatchSnapshot();
    });
});
