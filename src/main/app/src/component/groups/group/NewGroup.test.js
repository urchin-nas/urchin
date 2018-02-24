import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewGroup from "./NewGroup";

describe('NewGroup', () => {

    let callbacks = {
        setGroup: jest.fn(),
        createGroup: jest.fn(),
    };

    let props = {
        group: {},
        callbacks: callbacks
    };

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NewGroup {...props}/>))).toMatchSnapshot();
    });

    it('setGroup is called when groupName changes', () => {
        let component = shallow(<NewGroup {...props}/>);
        component.find('[name="groupName"]').simulate('change', {target: {value: "name"}});

        expect(callbacks.setGroup).toHaveBeenCalledTimes(1);
    });

    it('createGroup is called when clicking create', () => {
        let component = shallow(<NewGroup {...props}/>);
        component.find('[data-view="create"]').simulate('click');

        expect(callbacks.createGroup).toHaveBeenCalledWith(props.group)
    });
});
