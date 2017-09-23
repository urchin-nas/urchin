import React, {Component} from 'react';
import {Route, Switch} from 'react-router-dom'
import {NotificationContainer} from 'react-notifications';
import MainContainer from './component/MainContainer';
import LoginContainer from './component/LoginContainer';

class App extends Component {
    render() {
        return (
            <div className="urchin">
                <NotificationContainer/>
                <Switch>
                    <Route path='/login' component={LoginContainer}/>
                    <Route path='/' component={MainContainer}/>
                </Switch>
            </div>
        );
    }
}

export default App;
