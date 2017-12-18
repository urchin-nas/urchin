import React, {Component} from 'react'
import NavigationContainer from './navigation/NavigationContainer';
import RootRoute from "./RootRoute";

class Root extends Component {
    render() {
        return (
            <div className="root">
                <div className="root__navigation">
                    <NavigationContainer/>
                </div>
                <div className="root__content">
                    <RootRoute/>
                </div>
            </div>
        );
    }
}

export default Root;