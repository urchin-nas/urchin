import React, {Component} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import Users from "./users/Users";
import GroupRoutes from "./groups/GroupRoutes";
import MainMenu from "./MainMenu";
import Home from "./Home";
import Folders from "./folders/Folders";

class MainContainer extends Component {
    render() {
        return (
            <div>
                <MainMenu/>
                <Switch>
                    <Route exact path="/" component={Home}/>
                    <Route path="/users" component={Users}/>
                    <Route path="/groups" component={GroupRoutes}/>
                    <Route path="/folders" component={Folders}/>
                    <Redirect to="/"/>
                </Switch>
            </div>
        );
    }
}

export default MainContainer;