import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import ConfirmNewFolder from "./ConfirmNewFolder";

describe('ConfirmNewFolder', () => {

    let callbacks = {
        setEncryptedFolderPassphrase: jest.fn(),
        confirmEncryptedFolder: jest.fn(),
    };

    let props = {
        confirmNewFolder: {
            passphrase: 'random'
        },
        createdFolder: {
            id: 1,
            passphrase: 'randomString',
        },
        callbacks: callbacks
    };

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<ConfirmNewFolder {...props}/>))).toMatchSnapshot();
    });

    it('setEncryptedFolderPassphrase is called when passphrase changes', () => {
        let component = shallow(<ConfirmNewFolder {...props}/>);
        component.find('[name="passphrase"]').simulate('change', {target: {value: 'a'}});

        expect(callbacks.setEncryptedFolderPassphrase).toHaveBeenCalledTimes(1);
    });

    it('confirmEncryptedFolder is called when clicking confirm', () => {
        let component = shallow(<ConfirmNewFolder {...props}/>);
        component.find('[data-view="confirm"]').simulate('click');

        expect(callbacks.confirmEncryptedFolder).toHaveBeenCalledWith(props.createdFolder.id, props.confirmNewFolder.passphrase);
    });
});
