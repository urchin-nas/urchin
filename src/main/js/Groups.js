import React, {Component} from "react";

class Groups extends Component {

    render() {
        var groupList = this.props.groups.map(group =>
            <li key={group.groupId}>
                {group.name}
            </li>
        );

        return (
            <ul>
                {groupList}
            </ul>
        )
    }
}

Groups.propTypes = {
    groups: React.PropTypes.arrayOf(
        React.PropTypes.shape({
            groupId: React.PropTypes.number.isRequired,
            name: React.PropTypes.string.isRequired
        })
    )
};


export default Groups;