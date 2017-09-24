import React, {Component} from 'react'
import {connect} from 'react-redux'
import EditGroup from './EditGroup';
import {deleteGroup, getGroup, saveGroup, setGroup} from '../../../action/groupAction';

class EditGroupContainer extends Component {

    componentWillMount() {
        this.props.getGroup(this.getGroupId());
    }

    setGroup = (group) => {
        this.props.setGroup(group);
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
            <EditGroup
                groupId={groupId}
                group={this.props.group}
                fieldErrors={this.props.fieldErrors}
                callbacks={{
                    setGroup: this.setGroup,
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
        deleteGroup: (groupId) => {
            dispatch(deleteGroup(groupId))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        group: state.groupData.group || {},
        fieldErrors: state.groupData.fieldErrors || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(EditGroupContainer)