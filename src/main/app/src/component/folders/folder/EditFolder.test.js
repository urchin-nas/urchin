import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditFolder from "./EditFolder";

describe('EditFolder', () => {

    const callbacks = {
        deleteFolder: jest.fn(),
    };

    const props = {
        folder: {
            folderId: 1
        },
        usersInFolder: [],
        availableUsers: [],
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<EditFolder {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('deleteFolder is called when clicking delete', () => {
        component.find('[data-view="delete"]').simulate('click');

        expect(callbacks.deleteFolder).toHaveBeenCalledWith(props.folder.folderId)
    });
});
