import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import SetupAdmin from "./SetupAdmin";

describe('Setup', () => {

    const callbacks = {
        setAdmin: jest.fn(),
        setupAdmin: jest.fn(),
    };

    const props = {
        admin: {},
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<SetupAdmin {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('setAdmin is called when username changes', () => {
        component.find('[name="username"]').simulate('change', {target: {value: "name"}});

        expect(callbacks.setAdmin).toHaveBeenCalledTimes(1);
    });

    it('setupAdmin is called when clicking add', () => {
        component.find('[data-view="add"]').simulate('click');

        expect(callbacks.setupAdmin).toHaveBeenCalledWith(props.admin)
    });
});
