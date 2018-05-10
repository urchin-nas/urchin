import React, {Component} from 'react'

class Login extends Component {

    render() {
        return (
            <div data-view="login"
                 className="login">
                <form method="post" action="/login">
                    <input type="text" name="username" placeholder="Username"/>
                    <input type="password" name="password" placeholder="Password"/>
                    <input type="submit" name="login" value="Login"/>
                </form>
            </div>
        )
    }
}

export default Login