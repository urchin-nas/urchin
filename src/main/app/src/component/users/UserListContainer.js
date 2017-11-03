import React, {Component} from 'react'
import {connect} from 'react-redux'
import {getUsers} from '../../action/userAction'
import UserList from "./UserList";

export class UserListContainer extends Component {

    componentWillMount() {
        this.props.getUsers();
    }

    render() {
        return (
            <UserList
                users={this.props.users}
            />
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
        users: state.userData.users || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserListContainer)