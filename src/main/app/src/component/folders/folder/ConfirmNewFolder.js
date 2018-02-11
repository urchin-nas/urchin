import React, {Component} from 'react'
import FieldError from "../../FieldError";

class ConfirmNewFolder extends Component {

    update = (e) => {
        let data = {
            field: e.target.name,
            value: e.target.value
        };

        this.props.callbacks.setEncryptedFolderPassphrase(data);
    };

    confirm = () => {
        let folderId = this.props.createdFolder.id;
        this.props.callbacks.confirmEncryptedFolder(folderId, this.props.confirmNewFolder.passphrase);
    };

    render() {
        let passphrase = this.props.confirmNewFolder.passphrase || '';
        let passphraseToMatch = this.props.createdFolder !== undefined ? this.props.createdFolder.passphrase : undefined;

        let content = null;
        if (passphraseToMatch) {
            content = <div>
                <div>
                    Your folder has been created.
                    This is the randomly generated passphrase for your folder:
                </div>
                <p data-view="passphraseToMatch">
                    {passphraseToMatch}
                </p>
                <div>
                    Make sure to write down this passphrase and store it in a safe place!
                </div>
                <input name="passphrase"
                       type="password"
                       value={passphrase}
                       onChange={this.update}/>
                <FieldError fieldErrors={this.props.fieldErrors}
                            field="passphrase"/>
                <button data-view="confirm"
                        className="confirm-new-folder__confirm-btn"
                        onClick={this.confirm}>
                    Confirm
                </button>
            </div>;
        } else {
            content = <div>
                Undesired state! Passphrase is missing. Please delete newly created folder and try again.
            </div>
        }

        return (
            <div data-view="confirmNewFolder"
                 className="confirm-new-folder">
                {content}
            </div>
        )
    }
}

export default ConfirmNewFolder