import {combineReducers} from 'redux';
import userReducer from './userReducer';
import groupReducer from './groupReducer';
import folderReducer from './folderReducer';
import aclReducer from './aclReducer';
import siteReducer from './siteReducer';
import authReducer from './authReducer';
import adminReducer from './adminReducer'

const appReducer = combineReducers({
    userData: userReducer,
    groupData: groupReducer,
    folderData: folderReducer,
    aclData: aclReducer,
    siteData: siteReducer,
    authData: authReducer,
    adminData: adminReducer
});

const rootReducer = (state, action) => {
    return appReducer(state, action)
};

export default rootReducer;