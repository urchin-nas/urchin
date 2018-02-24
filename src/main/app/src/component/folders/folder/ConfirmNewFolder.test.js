import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import ConfirmNewFolder from "./ConfirmNewFolder";

describe('ConfirmNewFolder', () => {

    const callbacks = {
        setEncryptedFolderPassphrase: jest.fn(),
        confirmEncryptedFolder: jest.fn(),
    };

    const props = {
        confirmNewFolder: {
            passphrase: 'random'
        },
        createdFolder: {
            id: 1,
            passphrase: 'randomString',
        },
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<ConfirmNewFolder {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('setEncryptedFolderPassphrase is called when passphrase changes', () => {
        component.find('[name="passphrase"]').simulate('change', {target: {value: 'a'}});

        expect(callbacks.setEncryptedFolderPassphrase).toHaveBeenCalledTimes(1);
    });

    it('confirmEncryptedFolder is called when clicking confirm', () => {
        component.find('[data-view="confirm"]').simulate('click');

        expect(callbacks.confirmEncryptedFolder).toHaveBeenCalledWith(props.createdFolder.id, props.confirmNewFolder.passphrase);
    });
});
