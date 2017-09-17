import React, {Component} from "react";
import {connect} from "react-redux";
import EditUser from "./EditUser";
import {addGroup, deleteUser, getUser, setUser} from "../../../action/userAction";
import {getGroups} from "../../../action/groupAction";

class UserContainer extends Component {

    componentWillMount() {
        let userId = parseInt(this.props.match.params.id, 10);
        this.props.getUser(userId);
        this.props.getGroups();
    }

    render() {
        return (
            <EditUser
                user={this.props.user}
                availableGroups={this.props.availableGroups}
                callbacks={{
                    addGroup: this.props.addGroup,
                    deleteUser: this.props.deleteUser,
                    setUser: this.props.setUser
                }}
            />
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getUser: (userId) => {
            dispatch(getUser(userId))
        },
        setUser: (user) => {
            dispatch(setUser(user))
        },
        deleteUser: (userId) => {
            dispatch(deleteUser(userId))
        },
        getGroups: () => {
            dispatch(getGroups())
        },
        addGroup: (userId, groupId) => {
            dispatch(addGroup(userId, groupId))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        user: state.userData.user || {},
        availableGroups: state.groupData.groups || []
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(UserContainer)