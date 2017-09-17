import React, {Component} from "react";
import {connect} from "react-redux";
import NewUser from "./NewUser";
import {createUser, setUser} from "../../../action/userAction";

class NewUserContainer extends Component {

    render() {
        return (
            <NewUser
                user={this.props.user}
                callbacks={{
                    setUser: this.props.setUser,
                    createUser: this.props.createUser
                }}
            />
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setUser: (user) => {
            dispatch(setUser(user))
        },
        createUser: (user) => {
            dispatch(createUser(user))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        user: state.userData.user || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NewUserContainer)