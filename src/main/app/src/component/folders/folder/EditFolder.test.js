import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditFolder from "./EditFolder";

describe('EditFolder', () => {

    let callbacks = {
        deleteFolder: jest.fn(),
    };

    let props = {
        folder: {
            folderId: 1
        },
        usersInFolder: [],
        availableUsers: [],
        callbacks: callbacks
    };

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<EditFolder {...props}/>))).toMatchSnapshot();
    });

    it('deleteFolder is called when clicking delete', () => {
        let component = shallow(<EditFolder {...props}/>);
        component.find('[data-view="delete"]').simulate('click');

        expect(callbacks.deleteFolder).toHaveBeenCalledWith(props.folder.folderId)
    });
});
