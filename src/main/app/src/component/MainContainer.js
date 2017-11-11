import React, {Component} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import Users from "./users/Users";
import Groups from "./groups/Groups";
import MainMenu from "./MainMenu";
import Home from "./Home";
import Folders from "./folders/Folders";

class MainContainer extends Component {
    render() {
        return (
            <div className="main-container">
                <MainMenu/>
                <Switch>
                    <Route exact path="/" component={Home}/>
                    <Route path="/users" component={Users}/>
                    <Route path="/groups" component={Groups}/>
                    <Route path="/folders" component={Folders}/>
                    <Redirect to="/"/>
                </Switch>
            </div>
        );
    }
}

export default MainContainer;