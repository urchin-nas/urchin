import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewGroup from "./NewGroup";

describe('NewGroup', () => {

    const callbacks = {
        setGroup: jest.fn(),
        createGroup: jest.fn(),
    };

    const props = {
        group: {},
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<NewGroup {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('setGroup is called when groupName changes', () => {
        component.find('[name="groupName"]').simulate('change', {target: {value: "name"}});

        expect(callbacks.setGroup).toHaveBeenCalledTimes(1);
    });

    it('createGroup is called when clicking create', () => {
        component.find('[data-view="create"]').simulate('click');

        expect(callbacks.createGroup).toHaveBeenCalledWith(props.group)
    });
});
