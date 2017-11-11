import React, {Component} from 'react'

class MainMenu extends Component {

    render() {
        return (
            <div className="main-menu">
                MainMenu
                <ul>
                    <a href="/home">Home</a>
                </ul>
                <ul>
                    <a href="/users">Users</a>
                </ul>
                <ul>
                    <a href="/groups">Groups</a>
                </ul>
                <ul>
                    <a href="/folders">Folders</a>
                </ul>
            </div>
        )
    }
}

export default MainMenu