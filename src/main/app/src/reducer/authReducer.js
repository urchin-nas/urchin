import {Actions} from '../constants'

const authReducer = (state = {}, action) => {
    switch (action.type) {
        case Actions.Auth.LOGIN_INVALID:
        case Actions.Auth.LOGIN_FAILED:
        case Actions.Auth.LOGIN_SUCCESS:
        case Actions.Auth.PRECONDITION_FAILED:
        case Actions.Auth.LOGOUT_SUCCESS:
            return {...state, isLoggingIn: false, authed: action.type};
        case Actions.Auth.LOGIN:
            return {...state, isLoggingIn: true};
        default:
            return state;
    }
};

export default authReducer