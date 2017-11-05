import React from 'react';
import {mount, shallow} from 'enzyme'
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

    it('renders without crashing', () => {
        expect(mount(<EditFolderContainer {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<EditFolderContainer {...props}/>))).toMatchSnapshot();
    });
});
