import React, {Component} from 'react'
import {Switch, Route} from 'react-router-dom'
import UserListContainer from './UserListContainer';
import EditUserContainer from './user/EditUserContainer';
import NewUserContainer from './user/NewUserContainer';

class Users extends Component {

    render() {
        return (
            <Switch>
                <Route exact path="/users" component={UserListContainer}/>
                <Route path="/users/new" component={NewUserContainer}/>
                <Route path="/users/:id" component={EditUserContainer}/>
            </Switch>
        )
    }
}

export default Users