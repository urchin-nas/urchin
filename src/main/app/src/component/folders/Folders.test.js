import React from 'react';
import {mount, shallow} from 'enzyme'
import {Router} from "react-router-dom";
import toJson from 'enzyme-to-json';
import Folders from "./Folders";
import history from '../../history'

describe('Folders', () => {

    it('renders without crashing', () => {
        let component = mount(
            <Router history={history}>
                <Folders/>
            </Router>
        );
        expect(component.length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<Folders/>))).toMatchSnapshot();
    });
});
