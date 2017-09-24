import React, {Component} from 'react'

class FieldError extends Component {

    render() {
        let field = this.props.field;
        let errorMessage = this.props.fieldErrors[field];
        return (<div>
            {errorMessage &&
            <div className="fieldError">
                {errorMessage}
            </div>
            }
        </div>)
    }
}

export default FieldError