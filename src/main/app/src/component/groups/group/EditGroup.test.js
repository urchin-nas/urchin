import React from 'react';
import {shallow} from 'enzyme'
import toJson from 'enzyme-to-json';
import EditGroup from "./EditGroup";

describe('EditGroup', () => {
    let groupId = 10;
    let userId_1 = 100;
    let userId_2 = 101;

    let callbacks = {
        setGroup: jest.fn(),
        deleteGroup: jest.fn(),
        addUser: jest.fn(),
        removeUser: jest.fn(),
    };

    let props = {
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

    beforeEach(() => {
        Object.entries(callbacks).forEach(([name, fn]) => {
            fn.mockClear();
        });
    });

    it('match snapshot', () => {
        expect(toJson(shallow(<EditGroup {...props}/>))).toMatchSnapshot();
    });

    it('deleteGroup is called when clicking delete', () => {
        let component = shallow(<EditGroup {...props}/>);
        component.find('[data-view="delete"]').simulate('click');

        expect(callbacks.deleteGroup).toHaveBeenCalledWith(props.group.groupId)
    });

    it('setGroup is called when availableUsers select changes', () => {
        let component = shallow(<EditGroup {...props}/>);
        component.find('[data-view="availableUsers"]').simulate('change', {target: {value: userId_1}});

        expect(callbacks.setGroup).toHaveBeenCalledTimes(1);
    });

    it('addUser is called when clicking addUser', () => {
        let component = shallow(<EditGroup {...props}/>);
        component.find('[data-view="addUser"]').simulate('click');

        expect(callbacks.addUser).toHaveBeenCalledWith(groupId, userId_1)
    });

    it('removeUser is called when clicking removeUser', () => {
        let component = shallow(<EditGroup {...props}/>);
        component.find('[data-view="removeUser"]').simulate('click');

        expect(callbacks.removeUser).toHaveBeenCalledWith(groupId, userId_2)
    });
});
