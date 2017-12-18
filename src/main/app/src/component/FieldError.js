import React, {Component} from 'react'

class FieldError extends Component {

    render() {
        let field = this.props.field;
        let fieldErrors = this.props.fieldErrors || {};
        let errorMessages = fieldErrors[field] ? (fieldErrors[field].map((item, index) =>
            <div className="field-errors__message" key={index.toString()}>
                {item}
            </div>
        )) : null;

        return (
            <div>
                {errorMessages &&
                <div className="field-errors">
                    {errorMessages}
                </div>
                }
            </div>)
    }
}

export default FieldError