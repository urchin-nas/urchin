import React, {Component} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import Users from './users/Users';
import Groups from './groups/Groups';
import MainMenu from './MainMenu';
import Home from './Home';
import Folders from './folders/Folders';
import './MainContainer.css';

class MainContainer extends Component {
    render() {
        return (
            <div id="main-container">
                <div id="main-container__main-menu">
                    <MainMenu/>
                </div>
                <div id="main-container__content">
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

export default MainContainer;