import React, { Component } from 'react';
import './App.css';
import UserContainer from "./component/user/UserContainer";

class App extends Component {
  render() {
    return (
      <div className="App">
        <UserContainer/>
      </div>
    );
  }
}

export default App;
