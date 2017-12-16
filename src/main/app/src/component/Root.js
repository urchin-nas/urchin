import React, {Component} from 'react'
import NavigationContainer from './navigation/NavigationContainer';
import RootRoute from "./RootRoute";

class Root extends Component {
    render() {
        return (
            <div id="root">
                <div id="root__navigation">
                    <NavigationContainer/>
                </div>
                <div id="root__content">
                    <RootRoute/>
                </div>
            </div>
        );
    }
}

export default Root;