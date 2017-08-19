import React, {Component} from 'react'
import {Switch, Route} from 'react-router-dom'
import UserListContainer from './UserListContainer';
import UserContainer from './user/UserContainer';

class Users extends Component {

    render() {
        return (
            <Switch>
                <Route exact path="/users" component={UserListContainer}/>
                <Route path="/users/:id" component={UserContainer}/>
            </Switch>
        )
    }
}

export default Users