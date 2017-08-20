import React, {Component} from 'react'
import {connect} from 'react-redux'
import User from './User';
import {setUserData, saveUserData, getUserData, deleteUserData} from '../../../action/userAction';

class UserContainer extends Component {

    componentWillMount() {
        let userId = this.getUserId();
        if (userId > 0) {
            this.props.getUser(userId);
        }
    }

    setUser = (user) => {
        this.props.setUser(user);
    };

    saveUser = (userId) => {
        this.props.saveUser(userId, this.props.user);
    };

    deleteUser = (userId) => {
        this.props.deleteUser(userId);
    };

    getUserId = () => {
        return parseInt(this.props.match.params.id, 10);
    };

    render() {
        let userId = this.getUserId();
        return (
            <User
                userId={userId}
                user={this.props.user}
                callbacks={{
                    setUser: this.setUser,
                    saveUser: this.saveUser,
                    deleteUser: this.deleteUser
                }}
            />
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getUser: (userId) => {
            dispatch(getUserData(userId))
        },
        setUser: (user) => {
            dispatch(setUserData(user))
        },
        saveUser: (userId, user) => {
            dispatch(saveUserData(userId, user))
        },
        deleteUser: (userId) => {
            dispatch(deleteUserData(userId))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        user: state.userData.user || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserContainer)