import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditAcl from "./EditAcl";

describe('EditAcl', () => {
    const userId = 100;
    const groupId = 10;
    const username = 'username';
    const groupName = 'group';

    const callbacks = {
        updateAclForUser: jest.fn(),
        updateAclForGroup: jest.fn(),
    };
    const props = {
        aclUsers: [{
            userId: userId,
            username: username,
            read: false,
            write: false,
            execute: false
        }],
        aclGroups: [{
            groupId: groupId,
            groupName: groupName,
            read: true,
            write: true,
            execute: true
        }],
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<EditAcl {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('updateAclForUser is called when clicking on user read permission checkbox', () => {
        component.find('[data-view="' + username + '-read"]')
            .simulate('change', {target: {name: 'read', checked: true}});

        expect(callbacks.updateAclForUser).toHaveBeenCalledWith(userId, 'read', true)
    });

    it('updateAclForGroup is called when clicking on group read permission checkbox', () => {
        component.find('[data-view="' + groupName + '-read"]')
            .simulate('change', {target: {name: 'read', checked: false}});

        expect(callbacks.updateAclForGroup).toHaveBeenCalledWith(groupId, 'read', false)
    });
});
