import React from 'react';
import {mount, shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {NewFolderContainer} from "./NewFolderContainer";

describe('NewFolderContainer', () => {

    let props = {
        folder: {},
    };

    it('renders without crashing', () => {

        expect(mount(<NewFolderContainer {...props}/>).length).toEqual(1);
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<NewFolderContainer {...props}/>))).toMatchSnapshot();
    });
});
