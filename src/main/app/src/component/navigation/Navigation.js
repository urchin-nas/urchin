import React, {Component} from 'react'
import {Link} from "react-router-dom";

class Navigation extends Component {

    toggleNavigation = () => {
        this.props.callbacks.toggleNavigation(this.props.navigationVisible)
    };

    hideNavigation = () => {
        if (this.props.navigationVisible) {
            this.props.callbacks.toggleNavigation(this.props.navigationVisible)
        }
    };

    render() {
        let toggleNav = this.props.navigationVisible ? 'navigation-menu--show' : 'navigation-menu--hide';

        return (
            <nav data-view="navigation"
                 className="navigation">
                <Link data-view="navigationBrand"
                      className="navigation__brand"
                      to="/"
                      onClick={this.hideNavigation}>
                    Urchin
                </Link>
                <button data-view="toggleNavigation"
                        className="navigation--toggler"
                        onClick={this.toggleNavigation}>
                    <span className="navigation-toggler__icon"/>
                </button>
                <div className={`navigation-menu ${toggleNav}`}>
                    <ul className="navigation-menu__items">
                        <li
                            data-view="users"
                            className="navigation-item">
                            <Link className="navigation-item__users"
                                  to="/users"
                                  onClick={this.hideNavigation}>
                                Users
                            </Link>
                        </li>
                        <li data-view="groups"
                            className="navigation-item">
                            <Link className="navigation-item__groups"
                                  to="/groups"
                                  onClick={this.hideNavigation}>
                                Groups
                            </Link>
                        </li>
                        <li data-view="folders"
                            className="navigation-item">
                            <Link className="navigation-item__folders"
                                  to="/folders"
                                  onClick={this.hideNavigation}>
                                Folders
                            </Link>
                        </li>

                    </ul>
                </div>
            </nav>
        )
    }
}

export default Navigation