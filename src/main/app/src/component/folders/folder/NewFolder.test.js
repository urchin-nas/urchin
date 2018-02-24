import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import NewFolder from "./NewFolder";

describe('NewFolder', () => {

    let callbacks = {
        setFolder: jest.fn(),
        createFolder: jest.fn(),
    };

    let props = {
        folder: {},
        callbacks: callbacks
    };

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NewFolder {...props}/>))).toMatchSnapshot();
    });

    it('setFolder is called when folder name changes', () => {
        let component = shallow(<NewFolder {...props}/>);
        component.find('[name="folder"]').simulate('change', {target: {value: 'a'}});

        expect(callbacks.setFolder).toHaveBeenCalledTimes(1);
    });

    it('createFolder is called when clicking create', () => {
        let component = shallow(<NewFolder {...props}/>);
        component.find('[data-view="create"]').simulate('click');

        expect(callbacks.createFolder).toHaveBeenCalledWith(props.folder)
    });

});
