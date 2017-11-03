import React, {Component} from 'react'

class FieldError extends Component {

    render() {
        let field = this.props.field;
        let fieldErrors = this.props.fieldErrors || {};
        let errorMessages = fieldErrors[field] ? (fieldErrors[field].map((item, index) =>
            <div className="fieldError" key={index.toString()}>
                {item}
            </div>
        )) : null;

        return (<div>
            {errorMessages &&
            <div className="fieldErrors">
                {errorMessages}
            </div>
            }
        </div>)
    }
}

export default FieldError