import React, {Component} from 'react'
import {Route, Switch} from 'react-router-dom'
import UsersContainer from './UsersContainer';
import EditUserContainer from './user/EditUserContainer';
import NewUserContainer from './user/NewUserContainer';

class UsersRoute extends Component {

    render() {
        return (
            <Switch>
                <Route exact path="/users"
                       component={UsersContainer}/>
                <Route path="/users/new"
                       component={NewUserContainer}/>
                <Route path="/users/:id"
                       component={EditUserContainer}/>
            </Switch>
        )
    }
}

export default UsersRoute