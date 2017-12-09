import {Actions} from "../constants";

const siteReducer = (state = {}, action) => {
    switch (action.type) {
        case Actions.Site.TOGGLE_NAVIGATION:
            return {...state, navigationVisible: action.data};
        default:
            return state;
    }
};

export default siteReducer