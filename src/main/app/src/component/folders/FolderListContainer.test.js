import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {FolderListContainer} from "./FolderListContainer";

describe('FolderListContainer', () => {

    let props = {
        getFolders: jest.fn(),
    };

    it('renders without crashing', () => {
        expect(mount(<FolderListContainer {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<FolderListContainer {...props}/>))).toMatchSnapshot();
    });
});
