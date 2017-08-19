import React, {Component} from 'react'
import {connect} from 'react-redux'
import User from './User';
import {setUserData, saveUserData} from '../../../action/userAction';

class UserContainer extends Component {

    setUser = (user) => {
        this.props.setUser(user);
    };

    saveUser = (userId) => {
        this.props.saveUser(userId, this.props.user);
    };

    render() {
        let userId = parseInt(this.props.match.params.id, 10);
        return (
            <User
                userId={userId}
                user={this.props.user}
                callbacks={{
                    setUser: this.setUser,
                    saveUser: this.saveUser
                }}
            />
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setUser: (user) => {
            dispatch(setUserData(user))
        },
        saveUser: (userId, user) => {
            dispatch(saveUserData(userId, user))
        }
    }

};

const mapStateToProps = (state) => {
    return {
        user: state.userData.user || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserContainer)