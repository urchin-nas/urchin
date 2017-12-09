import React, {Component} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import Users from './users/Users';
import Groups from './groups/Groups';
import NavigationContainer from './navigation/NavigationContainer';
import Home from './Home';
import Folders from './folders/Folders';

class Root extends Component {
    render() {
        return (
            <div id="root">
                    <div id="root__navigation">
                        <NavigationContainer/>
                    </div>
                    <div id="root__content">
                        <Switch>
                            <Route exact path="/" component={Home}/>
                            <Route path="/users" component={Users}/>
                            <Route path="/groups" component={Groups}/>
                            <Route path="/folders" component={Folders}/>
                            <Redirect to="/"/>
                        </Switch>
                    </div>
                </div>
        );
    }
}

export default Root;