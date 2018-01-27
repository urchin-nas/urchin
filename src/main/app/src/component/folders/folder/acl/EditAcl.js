import React, {Component} from 'react'

class EditAcl extends Component {

    updateAclForUser = (userId, e) => {
        this.props.callbacks.updateAclForUser(userId, e.target.name, e.target.checked);
    };

    updateAclForGroup = (groupId, e) => {
        this.props.callbacks.updateAclForGroup(groupId, e.target.name, e.target.checked);
    };

    render() {
        let usersAcl = this.props.aclUsers || [];
        let groupsAcl = this.props.aclGroups || [];

        let userItems = usersAcl.map((item, index) =>
            <div className="edit-acl__users__user"
                 key={index.toString()}>
                <div>{item.username}</div>
                <div>
                    <input type="checkbox"
                           name="read"
                           checked={item.read}
                           onChange={(e) => this.updateAclForUser(item.userId, e)}
                    />
                </div>
                <div>
                    <input type="checkbox"
                           name="write"
                           checked={item.write}
                           onChange={(e) => this.updateAclForUser(item.userId, e)}
                    />
                </div>
                <div>
                    <input type="checkbox"
                           name="execute"
                           checked={item.execute}
                           onChange={(e) => this.updateAclForUser(item.userId, e)}
                    />
                </div>
            </div>
        );

        let groupItems = groupsAcl.map((item, index) =>
            <div className="edit-acl__groups__group"
                 key={index.toString()}>
                <div>{item.groupName}</div>
                <div>
                    <input type="checkbox"
                           name="read"
                           checked={item.read}
                           onChange={(e) => this.updateAclForGroup(item.groupId, e)}
                    />
                </div>
                <div>
                    <input type="checkbox"
                           name="write"
                           checked={item.write}
                           onChange={(e) => this.updateAclForGroup(item.groupId, e)}
                    />
                </div>
                <div>
                    <input type="checkbox"
                           name="execute"
                           checked={item.execute}
                           onChange={(e) => this.updateAclForGroup(item.groupId, e)}
                    />
                </div>
            </div>
        );

        return (
            <div data-view="editAcl"
                 className="edit-acl">
                <h2>Group permissions</h2>
                <div className="edit-acl__groups">
                    {groupItems}
                </div>
                <h2>User permissions</h2>
                <div className="edit-acl__users">
                    {userItems}
                </div>
            </div>

        )
    }
}

export default EditAcl