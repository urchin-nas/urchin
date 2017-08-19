import React, {Component} from 'react'
import {Switch, Route, Redirect} from 'react-router-dom'
import Users from "./users/Users";
import MainMenu from "./MainMenu";
import Home from "./Home";

class MainContainer extends Component {
    render() {
        return (
            <div>
                <MainMenu/>
                <Switch>
                    <Route exact path="/" component={Home}/>
                    <Route path="/users" component={Users}/>
                    <Redirect to="/"/>
                </Switch>
            </div>
        );
    }
}

export default MainContainer;