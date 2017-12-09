import React, {Component} from 'react'

class Navigation extends Component {

    toggleNavigation = () => {
        this.props.callbacks.toggleNavigation(this.props.navigationVisible)
    };

    render() {
        let toggleNav = this.props.navigationVisible ? 'navigation-menu--show' : 'navigation-menu--hide';

        return (
            <nav id="navigation">
                <a id="navigation__brand" href="/">Urchin</a>
                <button id="navigation--toggler" onClick={this.toggleNavigation}>
                    <span id="navigation-toggler__icon"/>
                </button>
                <div className={toggleNav} id="navigation-menu">
                    <ul id="navigation-menu__items">
                        <il className="navigation-item">
                            <a id="navigation-item__users" href="/users">Users</a>
                        </il>
                        <il className="navigation-item">
                            <a id="navigation-item__groups" href="/groups">Groups</a>
                        </il>
                        <il className="navigation-item">
                            <a id="navigation-item__folders" href="/folders">Folders</a>
                        </il>
                    </ul>
                </div>

            </nav>
        )
    }
}

export default Navigation