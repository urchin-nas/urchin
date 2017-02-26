import React, {Component} from "react";
import rest from "rest";

class Groups extends Component {

    constructor(props) {
        super(props);
        this.state = {groups: []};
    }

    componentDidMount() {
        rest({
            method: 'GET',
            path: '/api/groups'
        }).then(response => {
            this.setState({groups: JSON.parse(response.entity).data});
        });
    }

    render() {
        var groupList = this.state.groups.map(group =>
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