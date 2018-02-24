import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import Navigation from "./Navigation";

describe('Navigation', () => {

    const callbacks = {
        toggleNavigation: jest.fn(),
    };

    const props = {
        navigationVisible: false,
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<Navigation {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('toggleNavigation is called when clicking toggleNavigation', () => {
        component.find('[data-view="toggleNavigation"]').simulate('click');

        expect(callbacks.toggleNavigation).toHaveBeenCalledTimes(1)
    });

    it('toggleNavigation is called when visible while clicking navigation item', () => {
        let props = {
            navigationVisible: true,
            callbacks: callbacks
        };
        component = shallow(<Navigation {...props}/>);

        component.find('[data-view="navigationBrand"]').simulate('click');

        expect(callbacks.toggleNavigation).toHaveBeenCalledTimes(1)
    });

    it('toggleNavigation is not called when not visible while clicking navigation item', () => {
        component.find('[data-view="navigationBrand"]').simulate('click');

        expect(callbacks.toggleNavigation).toHaveBeenCalledTimes(0)
    });
});
