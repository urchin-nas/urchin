import React, {Component} from 'react'
import {Link} from "react-router-dom";

class Navigation extends Component {

    toggleNavigation = () => {
        this.props.callbacks.toggleNavigation(this.props.navigationVisible)
    };

    render() {
        let toggleNav = this.props.navigationVisible ? 'navigation-menu--show' : 'navigation-menu--hide';

        return (
            <nav id="navigation">
                <Link id="navigation__brand" to="/">Urchin</Link>
                <button id="navigation--toggler" onClick={this.toggleNavigation}>
                    <span id="navigation-toggler__icon"/>
                </button>
                <div className={toggleNav} id="navigation-menu">
                    <ul id="navigation-menu__items">
                        <il className="navigation-item">
                            <Link id="navigation-item__users" to="/users" onClick={this.toggleNavigation}>Users</Link>
                        </il>
                        <il className="navigation-item">
                            <Link id="navigation-item__groups" to="/groups"
                                  onClick={this.toggleNavigation}>Groups</Link>
                        </il>
                        <il className="navigation-item">
                            <Link id="navigation-item__folders" to="/folders"
                                  onClick={this.toggleNavigation}>Folders</Link>
                        </il>
                    </ul>
                </div>

            </nav>
        )
    }
}

export default Navigation