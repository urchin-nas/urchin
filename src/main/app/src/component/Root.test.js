import React from 'react';
import {Router} from 'react-router-dom'
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Root from "./Root";
import history from '../history'

describe('Root', () => {

    it('renders without crashing', () => {
        let component = shallow(
            <Router history={history}>
                <Root/>
            </Router>
        );

        expect(component.length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<Root/>))).toMatchSnapshot();
    });
});