import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditUser from "./EditUser";

describe('EditUser', () => {
    const userId = 1;
    const groupId_1 = 10;
    const groupId_2 = 11;

    const callbacks = {
        addGroup: jest.fn(),
        removeGroup: jest.fn(),
        deleteUser: jest.fn(),
        setUser: jest.fn(),
    };

    const props = {
        user: {
            userId: userId,
            groupId: groupId_1
        },
        groupsForUser: [{
            groupId: groupId_2,
            groupName: 'alreadyAddedGroup'
        }],
        availableGroups: [],
        callbacks: callbacks
    };

    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<EditUser {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('deleteUser is called when clicking delete', () => {
        component.find('[data-view="delete"]').simulate('click');

        expect(callbacks.deleteUser).toHaveBeenCalledWith(userId)
    });

    it('setUser is called when availableGroups select changes', () => {
        component.find('[data-view="availableGroups"]').simulate('change', {target: {value: groupId_1}});

        expect(callbacks.setUser).toHaveBeenCalledTimes(1);
    });

    it('addGroup is called when clicking addGroup', () => {
        component.find('[data-view="addGroup"]').simulate('click');

        expect(callbacks.addGroup).toHaveBeenCalledWith(userId, groupId_1)
    });

    it('removeGroup is called when clicking removeGroup', () => {
        component.find('[data-view="removeGroup"]').simulate('click');

        expect(callbacks.removeGroup).toHaveBeenCalledWith(userId, groupId_2)
    });
});
