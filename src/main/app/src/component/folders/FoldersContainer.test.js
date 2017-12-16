import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {FoldersContainer} from "./FoldersContainer";

describe('FoldersContainer', () => {

    let props = {
        getFolders: jest.fn(),
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<FoldersContainer {...props}/>))).toMatchSnapshot();
    });
});
