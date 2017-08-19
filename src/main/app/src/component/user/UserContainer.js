import React, {Component} from 'react'
import {connect} from 'react-redux'
import UserList from './UserList';
import {getUsers} from '../../action/userAction'

class UserContainer extends Component {

    componentWillMount() {
        this.props.getUsers();
    }

    render() {
        return(
            <UserList/>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getUsers: () => {
            dispatch(getUsers())
        }
    }
};

const mapStateToProps = (state) => {
    return {
        users: state.users || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserContainer)