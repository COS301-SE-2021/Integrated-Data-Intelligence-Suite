import React from 'react';
import { AiOutlineUpload } from 'react-icons/all';
import './UploadButton.css';

export default class UploadButton extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <button id={'upload-btn'}>
                    <AiOutlineUpload id={'upload-btn-logo'}/>
                    Upload
                </button>
            </>
        );
    }
}
