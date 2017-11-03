import React, {Component} from 'react';
import {Route, Router, Switch} from 'react-router-dom'
import history from './history'
import {NotificationContainer} from 'react-notifications';
import MainContainer from './component/MainContainer';
import LoginContainer from './component/LoginContainer';

class App extends Component {
    render() {
        return (
            <div className="urchin">
                <NotificationContainer/>
                <Router history={history}>
                    <Switch>
                        <Route path='/login' component={LoginContainer}/>
                        <Route path='/' component={MainContainer}/>
                    </Switch>
                </Router>
            </div>
        );
    }
}

export default App;
