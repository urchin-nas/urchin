import React, {Component} from 'react'
import {connect} from 'react-redux'
import NewGroup from './NewGroup';
import {createGroup, setGroup} from '../../../action/groupAction';

export class NewGroupContainer extends Component {

    render() {
        return (
            <NewGroup group={this.props.group}
                      fieldErrors={this.props.fieldErrors}
                      callbacks={{
                          setGroup: this.props.setGroup,
                          createGroup: this.props.createGroup,
                      }}/>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setGroup: (group) => {
            dispatch(setGroup(group))
        },
        createGroup: (group) => {
            dispatch(createGroup(group))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        group: state.groupData.group || {},
        fieldErrors: state.groupData.fieldErrors || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NewGroupContainer)