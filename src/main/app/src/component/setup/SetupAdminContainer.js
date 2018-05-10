import React, {Component} from 'react'
import SetupAdmin from "./SetupAdmin";
import {connect} from "react-redux";
import {setAdmin, setupAdmin} from "../../action/adminAction";

export class SetupAdminContainer extends Component {
    render() {
        return (
            <SetupAdmin admin={this.props.admin}
                        fieldErrors={this.props.fieldErrors}
                        callbacks={{
                            setAdmin: this.props.setAdmin,
                            setupAdmin: this.props.setupAdmin
                        }}/>
        );
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        setAdmin: (admin) => {
            dispatch(setAdmin(admin))
        },
        setupAdmin: (admin) => {
            dispatch(setupAdmin(admin))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        admin: state.adminData.admin || {},
        fieldErrors: state.adminData.fieldErrors || {}
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(SetupAdminContainer)