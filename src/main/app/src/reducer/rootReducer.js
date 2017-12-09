import {combineReducers} from 'redux';
import userReducer from './userReducer';
import groupReducer from './groupReducer';
import folderReducer from "./folderReducer";
import siteReducer from "./siteReducer";

const appReducer = combineReducers({
    userData: userReducer,
    groupData: groupReducer,
    folderData: folderReducer,
    siteData: siteReducer
});

const rootReducer = (state, action) => {
    return appReducer(state, action)
};

export default rootReducer;