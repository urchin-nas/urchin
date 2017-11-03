import React from 'react';
import {mount} from 'enzyme'
import App from './App';

describe('App', () => {

    it('renders without crashing', () => {
        expect(mount(<App/>).length).toEqual(1);
    });
});
