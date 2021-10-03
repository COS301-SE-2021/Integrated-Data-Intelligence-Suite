import React, { Component } from 'react';
import './UploadDataPage.css';
import UploadDropZone from '../../components/UploadDropZone/UploadDropZone';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';
import CustomDivider from '../../components/CustomDivider/CustomDivider';
import { styled } from '@mui/material/styles';

export default class UploadDataPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = { fileArray: '' };
        this.setFileArrayObj = this.setFileArrayObj.bind(this);
    }

    state = {
        fileArray: ''
    };

    handleOnClick() {
        //getting content inside the 4 edit boxes
        let text_message = document.getElementById('upload-input-text-msg').value;
        let location = document.getElementById('upload-input-location').value;
        let likes = document.getElementById('upload-input-likes').value;
        let date = document.getElementById('upload-input-date').value;

        // getting file array
        console.log(`file array obj double check: ${this.state.fileArray}`);

        // Make Post request to backend
        /*
           -  API_REQUEST_OBJ: 
           -  API_RESPONSE_OBJ: same as search
        */
        let url = '/analyzeUpload';
        let API_REQUEST_BODY = {
            file: this.state.fileArray,
            c1: text_message,
            c2: location,
            c3: likes,
            c4: date,
            c5: 'modelID'
        };
        console.log(API_REQUEST_BODY);

        let API_REQUEST_OBJ = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(API_REQUEST_BODY),
        };

        let API_RESPONSE_OBJ = null;
        fetch(`http://localhost:9000${url}`, API_REQUEST_OBJ)
            .then((response) => response.json())
            .then((json) => {
                API_RESPONSE_OBJ = json;
                this.handleTextChange(API_RESPONSE_OBJ);
            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
            });
    }

    setFileArrayObj(file_array_obj) {
        this.setState({ fileArray: file_array_obj }, () => {
            console.log(`file array obj: ${this.state.fileArray}`);
        });
    }

    render() {
        return (
            <>
                <div id={'upload-content-div'}>
                    <CustomDivider DividerTitle={'Upload your file'}/>
                    <UploadDropZone
                        setFileArray={this.setFileArrayObj}
                    />
                    <CustomDivider DividerTitle={'Match Columns'}/>
                    <UploadSchemaForm/>
                    <button
                        id={'analyse-upload-btn'}
                        onClick={() => this.handleOnClick()}
                    >
                        Analyze
                    </button>
                </div>
            </>
        );
    }
}


