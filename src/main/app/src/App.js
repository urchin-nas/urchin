import React, {Component} from 'react';
import {Route, Router, Switch} from 'react-router-dom'
import history from './history'
import {NotificationContainer} from 'react-notifications';
import Root from './component/Root';
import LoginContainer from './component/LoginContainer';
import ScrollToTop from "./component/ScrollToTop";

class App extends Component {
    render() {
        return (
            <div className="urchin">
                <NotificationContainer/>
                <Router history={history}>
                    <ScrollToTop>
                        <Switch>
                            <Route path='/login'
                                   component={LoginContainer}/>
                            <Route path='/'
                                   component={Root}/>
                        </Switch>
                    </ScrollToTop>
                </Router>
            </div>
        );
    }
}

export default App;
