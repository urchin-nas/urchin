import React from 'react';
import ReactDOM from 'react-dom';
import FieldError from './FieldError';

it('renders without crashing', () => {
    ReactDOM.render(<FieldError/>, document.createElement('div'));
});
