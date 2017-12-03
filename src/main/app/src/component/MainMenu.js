import React, {Component} from 'react'

class MainMenu extends Component {

    render() {
        return (
            <div id="main-menu">
                <div id="main-menu__logo">
                </div>
                <div id="main-menu__links">
                    <ul>
                        <a id="main-menu__links__home" href="/home">Home</a>
                    </ul>
                    <ul>
                        <a id="main-menu__links__users" href="/users">Users</a>
                    </ul>
                    <ul>
                        <a id="main-menu__links__groups" href="/groups">Groups</a>
                    </ul>
                    <ul>
                        <a id="main-menu__links__folders" href="/folders">Folders</a>
                    </ul>
                </div>
            </div>
        )
    }
}

export default MainMenu