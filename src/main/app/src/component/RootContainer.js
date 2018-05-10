import React, {Component} from 'react'
import {connect} from "react-redux";
import {isAuthenticated} from "../action/authAction";
import {Actions} from "../constants";
import Root from "./Root";
import {Redirect} from "react-router-dom";

const {Auth} = Actions;

export class RootContainer extends Component {

    componentWillMount() {
        this.props.isAuthenticated();
    }

    render() {
        const authed = this.props.authed;
        let content;

        switch (true) {
            case authed === Auth.LOGIN_SUCCESS:
                content = <Root/>;
                break;
            case authed === Auth.LOGIN_INVALID:
            case authed === Auth.LOGIN_FAILED:
            case authed === Auth.LOGOUT_SUCCESS:
                content = <Redirect to={"/login"}/>;
                break;
            case authed === Auth.PRECONDITION_FAILED:
                content = <Redirect to={"/setup-admin"}/>;
                break;
            default:
                content = <div/>
        }

        return (
            content
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        isAuthenticated: () => {
            dispatch(isAuthenticated())
        }
    }
};

const mapStateToProps = (state) => {
    return {
        authed: state.authData.authed
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(RootContainer)