import React, {Component} from "react";
import {connect} from "react-redux";
import NewUser from "./NewUser";
import {createUser, setUser} from "../../../action/userAction";

export class NewUserContainer extends Component {

    render() {
        return (
            <NewUser user={this.props.user}
                     fieldErrors={this.props.fieldErrors}
                     callbacks={{
                         setUser: this.props.setUser,
                         createUser: this.props.createUser
                     }}/>
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
        user: state.userData.user || {},
        fieldErrors: state.userData.fieldErrors || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NewUserContainer)