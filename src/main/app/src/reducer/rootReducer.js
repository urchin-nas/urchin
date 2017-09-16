import {combineReducers} from 'redux';
import userReducer from './userReducer';
import groupReducer from './groupReducer';

const appReducer = combineReducers({
    userData: userReducer,
    groupData: groupReducer
});

const rootReducer = (state, action) => {
    return appReducer(state, action)
};

export default rootReducer;