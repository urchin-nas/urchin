import React, {Component} from 'react'
import {Switch, Route} from 'react-router-dom'
import GroupListContainer from './GroupListContainer';
import GroupContainer from './group/GroupContainer';

class Groups extends Component {

    render() {
        return (
            <Switch>
                <Route exact path="/groups" component={GroupListContainer}/>
                <Route path="/groups/:id" component={GroupContainer}/>
            </Switch>
        )
    }
}

export default Groups