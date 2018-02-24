import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewFolder from "./NewFolder";

describe('NewFolder', () => {

    const callbacks = {
        setFolder: jest.fn(),
        createFolder: jest.fn(),
    };

    const props = {
        folder: {},
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<NewFolder {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('setFolder is called when folder name changes', () => {
        component.find('[name="folder"]').simulate('change', {target: {value: 'a'}});

        expect(callbacks.setFolder).toHaveBeenCalledTimes(1);
    });

    it('createFolder is called when clicking create', () => {
        component.find('[data-view="create"]').simulate('click');

        expect(callbacks.createFolder).toHaveBeenCalledWith(props.folder)
    });

});
