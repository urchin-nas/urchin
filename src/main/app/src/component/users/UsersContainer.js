import React, {Component} from 'react'
import {connect} from 'react-redux'
import {getUsers} from '../../action/userAction'
import Users from "./Users";

export class UsersContainer extends Component {

    componentWillMount() {
        this.props.getUsers();
    }

    render() {
        return (
            <Users users={this.props.users}/>
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

export default connect(mapStateToProps, mapDispatchToProps)(UsersContainer)