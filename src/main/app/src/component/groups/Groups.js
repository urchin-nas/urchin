import React, {Component} from 'react'
import {Switch, Route} from 'react-router-dom'
import GroupListContainer from './GroupListContainer';
import EditGroupContainer from './group/EditGroupContainer';
import NewGroupContainer from "./group/NewGroupContainer";

class Groups extends Component {

    render() {
        return (
            <Switch>
                <Route exact path="/groups" component={GroupListContainer}/>
                <Route path="/groups/new" component={NewGroupContainer}/>
                <Route path="/groups/:id" component={EditGroupContainer}/>
            </Switch>
        )
    }
}

export default Groups