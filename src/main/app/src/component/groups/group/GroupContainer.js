import React, {Component} from 'react'
import {connect} from 'react-redux'
import Group from './Group';
import {setGroup, saveGroup, getGroup, deleteGroup} from '../../../action/groupAction';

class GroupContainer extends Component {

    componentWillMount() {
        let groupId = this.getGroupId();
        if (groupId > 0) {
            this.props.getGroup(groupId);
        }
    }

    setGroup = (group) => {
        this.props.setGroup(group);
    };

    saveGroup = (groupId) => {
        this.props.saveGroup(groupId, this.props.group);
    };

    deleteGroup = (groupId) => {
        this.props.deleteGroup(groupId);
    };

    getGroupId = () => {
        return parseInt(this.props.match.params.id, 10);
    };

    render() {
        let groupId = this.getGroupId();
        return (
            <Group
                groupId={groupId}
                group={this.props.group}
                callbacks={{
                    setGroup: this.setGroup,
                    saveGroup: this.saveGroup,
                    deleteGroup: this.deleteGroup
                }}
            />
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getGroup: (groupId) => {
            dispatch(getGroup(groupId))
        },
        setGroup: (group) => {
            dispatch(setGroup(group))
        },
        saveGroup: (groupId, group) => {
            dispatch(saveGroup(groupId, group))
        },
        deleteGroup: (groupId) => {
            dispatch(deleteGroup(groupId))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        group: state.groupData.group || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(GroupContainer)