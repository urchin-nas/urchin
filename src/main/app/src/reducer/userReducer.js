import {Actions} from '../constants'

const userReducer = (state = {}, action) => {
    console.log(action);
    console.log(state);
    switch(action.type) {
        case Actions.Users.GET_USERS:
            return {...state, isFetchingUsers: true};

        case Actions.Users.RECEIVED_USERS:
            return {...state, isFetchingUsers: false, users: action.data};
        default:
            return state;
    }
};

export default userReducer