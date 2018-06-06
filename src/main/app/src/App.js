import React, {Component} from 'react';
import {Route, Router, Switch} from 'react-router-dom'
import history from './history'
import {NotificationContainer} from 'react-notifications';
import RootContainer from './component/RootContainer';
import LoginContainer from './component/login/LoginContainer';
import ScrollToTop from "./component/ScrollToTop";
import SetupAdminContainer from "./component/setup/SetupAdminContainer";

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
                            <Route path='/setup-admin'
                                   component={SetupAdminContainer}/>
                            <Route path='/'
                                   component={RootContainer}/>
                        </Switch>
                    </ScrollToTop>
                </Router>
            </div>
        );
    }
}

export default App;
