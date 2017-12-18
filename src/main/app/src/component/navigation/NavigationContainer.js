import React, {Component} from 'react'
import {connect} from 'react-redux'
import {toggleNavigation} from '../../action/siteAction';
import Navigation from './Navigation';

export class NavigationContainer extends Component {

    render() {
        return (
            <Navigation navigationVisible={this.props.navigationVisible}
                        callbacks={{
                            toggleNavigation: this.props.toggleNavigation,
                        }}/>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        toggleNavigation: (navigationVisible) => {
            dispatch(toggleNavigation(navigationVisible))
        }
    }
};

const mapStateToProps = (state) => {
    return {
        navigationVisible: state.siteData.navigationVisible || false
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(NavigationContainer)