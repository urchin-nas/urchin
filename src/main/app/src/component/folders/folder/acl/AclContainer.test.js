import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import {AclContainer} from "./AclContainer";

describe('AclContainer', () => {

    let props = {
        folderId: 1,
        acl: {
            users: [],
            groups: []
        },
        users: [],
        groups: []
    };

    it('match snapshot', () => {
        expect(toJson(shallow(<AclContainer {...props}/>))).toMatchSnapshot();
    });
});
