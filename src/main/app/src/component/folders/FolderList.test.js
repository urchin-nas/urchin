import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import FolderList from "./FolderList";

describe('FolderList', () => {

    it('renders without crashing', () => {
        expect(mount(<FolderList/>).length).toEqual(1);
    });


    it('match snapshot', () => {
        expect(toJson(shallow(<FolderList/>))).toMatchSnapshot();
    });
});
