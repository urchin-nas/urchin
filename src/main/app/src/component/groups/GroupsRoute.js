import React, {Component} from 'react'
import {Route, Switch} from 'react-router-dom'
import GroupsContainer from './GroupsContainer';
import EditGroupContainer from './group/EditGroupContainer';
import NewGroupContainer from "./group/NewGroupContainer";

class GroupsRoute extends Component {

    render() {
        return (
            <Switch>
                <Route exact path="/groups"
                       component={GroupsContainer}/>
                <Route path="/groups/new"
                       component={NewGroupContainer}/>
                <Route path="/groups/:id"
                       component={EditGroupContainer}/>
            </Switch>
        )
    }
}

export default GroupsRoute