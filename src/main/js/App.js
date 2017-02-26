import React, {Component} from "react";
import rest from "rest";
import Groups from "./Groups";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {groups: []};
    }

    componentDidMount() {
        rest({
            method: 'GET',
            path: '/api/groups'
        }).then(response => {
            this.setState({groups: JSON.parse(response.entity).data});
        });
    }

    render() {
        return (
            <Groups groups={this.state.groups}/>
        );
    }
}

export default App;