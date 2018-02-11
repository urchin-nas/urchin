import React, {Component} from 'react'
import {Route, Switch} from 'react-router-dom'
import FoldersContainer from './FoldersContainer';
import EditFolderContainer from './folder/EditFolderContainer';
import NewFolderContainer from "./folder/NewFolderContainer";
import ConfirmNewFolderContainer from "./folder/ConfirmNewFolderContainer";

class FoldersRoute extends Component {

    render() {
        return (
            <Switch>
                <Route exact path="/folders"
                       component={FoldersContainer}/>
                <Route path="/folders/new/confirm"
                       component={ConfirmNewFolderContainer}/>
                <Route path="/folders/new"
                       component={NewFolderContainer}/>
                <Route path="/folders/:id"
                       component={EditFolderContainer}/>
            </Switch>
        )
    }
}

export default FoldersRoute