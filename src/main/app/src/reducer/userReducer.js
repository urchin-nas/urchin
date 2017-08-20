import {Actions} from '../constants'

const userReducer = (state = {}, action) => {
    switch(action.type) {
        case Actions.Users.GET_USERS:
            return {...state, isFetchingUsers: true};

        case Actions.Users.GET_USERS_SUCCESS:
            return {...state, isFetchingUsers: false, users: action.data};

        case Actions.User.GET_USER:
            return {...state, isFetchingUser: true};

        case Actions.User.GET_USER_SUCCESS:
            return {...state, isFetchingUser: false, user: action.data};

        case Actions.User.SET_USER:
            return {...state, user: {...state.user, [action.data.field]: action.data.value}};

        case Actions.User.SAVE_USER:
            return{...state, isSavingUser: true};

            case Actions.User.SAVE_USER_SUCCESS:
            return{...state, isSavingUser: false};

        default:
            return state;
    }
};

export default userReducer