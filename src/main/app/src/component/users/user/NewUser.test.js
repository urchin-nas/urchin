import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewUser from "./NewUser";

describe('NewUser', () => {

    const callbacks = {
        setUser: jest.fn(),
        createUser: jest.fn(),
    };

    const props = {
        user: {},
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<NewUser {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('setUser is called when username changes', () => {
        component.find('[name="username"]').simulate('change', {target: {value: "name"}});

        expect(callbacks.setUser).toHaveBeenCalledTimes(1);
    });

    it('createUser is called when clicking create', () => {
        component.find('[data-view="create"]').simulate('click');

        expect(callbacks.createUser).toHaveBeenCalledWith(props.user)
    });
});
