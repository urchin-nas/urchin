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
                <div data-view={item.username}>{item.username}</div>
                <div>
                    <label><input data-view={`${item.username}-read`}
                                  type="checkbox"
                                  name="read"
                                  checked={item.read}
                                  onChange={(e) => this.updateAclForUser(item.userId, e)}
                    />
                        read
                    </label>
                </div>
                <div>
                    <label>
                        <input data-view={`${item.username}-write`}
                               type="checkbox"
                               name="write"
                               checked={item.write}
                               onChange={(e) => this.updateAclForUser(item.userId, e)}
                        />
                        write
                    </label>
                </div>
                <div>
                    <label>
                        <input data-view={`${item.username}-execute`}
                               type="checkbox"
                               name="execute"
                               checked={item.execute}
                               onChange={(e) => this.updateAclForUser(item.userId, e)}
                        />
                        execute
                    </label>
                </div>
            </div>
        );

        let groupItems = groupsAcl.map((item, index) =>
            <div className="edit-acl__groups__group"
                 key={index.toString()}>
                <div data-view={item.groupName}>{item.groupName}</div>
                <div>
                    <label>
                        <input data-view={`${item.groupName}-read`}
                               type="checkbox"
                               name="read"
                               checked={item.read}
                               onChange={(e) => this.updateAclForGroup(item.groupId, e)}
                        />
                        read
                    </label>
                </div>
                <div>
                    <label>
                        <input data-view={`${item.groupName}-write`}
                               type="checkbox"
                               name="write"
                               checked={item.write}
                               onChange={(e) => this.updateAclForGroup(item.groupId, e)}
                        />
                        write
                    </label>
                </div>
                <div>
                    <label>
                        <input data-view={`${item.groupName}-execute`}
                               type="checkbox"
                               name="execute"
                               checked={item.execute}
                               onChange={(e) => this.updateAclForGroup(item.groupId, e)}
                        />
                        execute
                    </label>
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