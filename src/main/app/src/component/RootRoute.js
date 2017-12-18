import React, {Component} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import UsersRoute from './users/UsersRoute';
import GroupsRoute from './groups/GroupsRoute';
import Home from './Home';
import FoldersRoute from './folders/FoldersRoute';

class RootRoute extends Component {
    render() {
        return (
            <Switch>
                <Route exact path="/"
                       component={Home}/>
                <Route path="/users"
                       component={UsersRoute}/>
                <Route path="/groups"
                       component={GroupsRoute}/>
                <Route path="/folders"
                       component={FoldersRoute}/>
                <Redirect to="/"/>
            </Switch>
        );
    }
}

export default RootRoute;