import React, {Component} from 'react'

class MainMenu extends Component {

    render() {
        return (
            <div id="main-menu">
                MainMenu
                <ul>
                    <a id="main-menu__home" href="/home">Home</a>
                </ul>
                <ul>
                    <a id="main-menu__users" href="/users">Users</a>
                </ul>
                <ul>
                    <a id="main-menu__groups" href="/groups">Groups</a>
                </ul>
                <ul>
                    <a id="main-menu__folders" href="/folders">Folders</a>
                </ul>
            </div>
        )
    }
}

export default MainMenu