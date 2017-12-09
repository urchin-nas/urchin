import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import App from './App';

describe('App', () => {

    it('renders without crashing', () => {
        expect(shallow(<App/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<App/>))).toMatchSnapshot();
    });
});
