import {Actions} from '../constants'

const adminReducer = (state = {}, action) => {
    switch (action.type) {
        case Actions.Admin.SET_ADMIN:
            return {...state, admin: {...state.admin, [action.data.field]: action.data.value}};
        case Actions.Admin.SETUP_ADMIN:
            return {...state, isAddingAdmin: true};
        case Actions.Admin.SETUP_ADMIN_SUCCESS:
            return {...state, isAddingAdmin: false};
        default:
            return state;
    }
};

export default adminReducer