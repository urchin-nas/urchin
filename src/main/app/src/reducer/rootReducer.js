import {combineReducers} from 'redux';
import userReducer from './userReducer';
import groupReducer from './groupReducer';
import folderReducer from "./folderReducer";

const appReducer = combineReducers({
    userData: userReducer,
    groupData: groupReducer,
    folderData: folderReducer
});

const rootReducer = (state, action) => {
    return appReducer(state, action)
};

export default rootReducer;