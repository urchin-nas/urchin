import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditGroup from "./EditGroup";

describe('EditGroup', () => {
    const groupId = 10;
    const userId_1 = 100;
    const userId_2 = 101;

    const callbacks = {
        setGroup: jest.fn(),
        deleteGroup: jest.fn(),
        addUser: jest.fn(),
        removeUser: jest.fn(),
    };

    const props = {
        group: {
            groupId: groupId,
            userId: userId_1
        },
        usersInGroup: [{
            userId: userId_2,
            username: 'user2'
        }],
        availableUsers: [{
            userId: userId_1,
            username: 'user1'
        }],
        callbacks: callbacks
    };
    let component;

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
        component = shallow(<EditGroup {...props}/>);
    });

    it('match snapshot', () => {
        expect(toJson(component)).toMatchSnapshot();
    });

    it('deleteGroup is called when clicking delete', () => {
        component.find('[data-view="delete"]').simulate('click');

        expect(callbacks.deleteGroup).toHaveBeenCalledWith(groupId)
    });

    it('setGroup is called when availableUsers select changes', () => {
        component.find('[data-view="availableUsers"]').simulate('change', {target: {value: userId_1}});

        expect(callbacks.setGroup).toHaveBeenCalledTimes(1);
    });

    it('addUser is called when clicking addUser', () => {
        component.find('[data-view="addUser"]').simulate('click');

        expect(callbacks.addUser).toHaveBeenCalledWith(groupId, userId_1)
    });

    it('removeUser is called when clicking removeUser', () => {
        component.find('[data-view="removeUser"]').simulate('click');

        expect(callbacks.removeUser).toHaveBeenCalledWith(groupId, userId_2)
    });
});
