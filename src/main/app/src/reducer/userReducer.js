import {Actions} from '../constants'

const userReducer = (state = {}, action) => {
    switch (action.type) {
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
            return {...state, isSavingUser: true};

        case Actions.User.SAVE_USER_SUCCESS:
            return {...state, isSavingUser: false};

        case Actions.User.SAVE_USER_VALIDATION_ERROR:
            return {...state, fieldErrors: action.data.fieldErrors};

        case Actions.User.GET_GROUPS_FOR_USER:
            return {...state, isFetchingGroupsForUser: true};

        case Actions.User.GET_GROUPS_FOR_USER_SUCCESS:
            return {...state, isFetchingGroupsForUser: false, groupsForUser: action.data};


        default:
            return state;
    }
};

export default userReducer