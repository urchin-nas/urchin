import React, {Component} from 'react'

class MainMenu extends Component {

    render() {
        return (
            <div>MainMenu
                <ul>
                    <a href="/home">Home</a>
                </ul>
                <ul>
                    <a href="/users">Users</a>
                </ul>
            </div>
        )
    }
}

export default MainMenu