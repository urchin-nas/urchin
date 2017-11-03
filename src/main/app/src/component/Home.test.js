import React from 'react';
import ReactDOM from 'react-dom';
import Home from "./Home";

it('renders without crashing', () => {
    ReactDOM.render(<Home/>, document.createElement('div'));
});
