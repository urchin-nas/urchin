import React, {Component} from 'react';
import {Switch, Route} from 'react-router-dom'
import MainContainer from './component/MainContainer';
import LoginContainer from './component/LoginContainer';

class App extends Component {
    render() {
        return (
            <Switch>
                <Route path='/login' component={LoginContainer}/>
                <Route path='/' component={MainContainer}/>
            </Switch>
        );
    }
}

export default App;
