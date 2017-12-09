import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {EditFolderContainer} from "./EditFolderContainer";

describe('EditFolderContainer', () => {

    let props = {
        match: {
            params: {
                id: 0
            }
        },
        folder: {},
        usersInFolder: [],
        users: [],
        getFolder: jest.fn(),
        getUsersForFolder: jest.fn(),
        getUsers: jest.fn(),
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<EditFolderContainer {...props}/>))).toMatchSnapshot();
    });
});
