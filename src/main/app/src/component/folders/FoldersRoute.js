import React, {Component} from 'react'
import {Route, Switch} from 'react-router-dom'
import FolderListContainer from './FolderListContainer';
import EditFolderContainer from './folder/EditFolderContainer';
import NewFolderContainer from "./folder/NewFolderContainer";

class FoldersRoute extends Component {

    render() {
        return (
            <Switch>
                <Route exact path="/folders" component={FolderListContainer}/>
                <Route path="/folders/new" component={NewFolderContainer}/>
                <Route path="/folders/:id" component={EditFolderContainer}/>
            </Switch>
        )
    }
}

export default FoldersRoute