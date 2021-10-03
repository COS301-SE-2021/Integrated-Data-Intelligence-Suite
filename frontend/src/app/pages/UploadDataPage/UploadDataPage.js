import React, { Component } from 'react';
import './UploadDataPage.css';
import UploadDropZone from '../../components/UploadDropZone/UploadDropZone';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';
import CustomDivider from '../../components/CustomDivider/CustomDivider';
import { useRecoilValue } from 'recoil';
import { uploadedCSVFileState } from '../../assets/AtomStore/AtomStore';

export default function UploadDataPage(props) {
    const uploadedAnalysingCSVFile = useRecoilValue(uploadedCSVFileState);
    const handleOnClick = () => {
        //getting content inside the 4 edit boxes
        let text_message = document.getElementById('upload-input-text-msg').value;
        let location = document.getElementById('upload-input-location').value;
        let likes = document.getElementById('upload-input-likes').value;
        let date = document.getElementById('upload-input-date').value;

        // getting file array
        console.log(`file array obj double check: ${uploadedAnalysingCSVFile}`);

        // Make Post request to backend
        /*
           -  API_REQUEST_OBJ: input box values, file array
           -  API_RESPONSE_OBJ: same as search
        */
        let url = '/analyzeUpload';
        let API_REQUEST_BODY = {
            file: uploadedAnalysingCSVFile,
            c1: text_message,
            c2: location,
            c3: likes,
            c4: date,
            modelID: 'Default'
        };
        console.log(API_REQUEST_BODY);

        const formData = new FormData();
        // formData.append('file', API_REQUEST_BODY.file);
        formData.append('file', new Blob(API_REQUEST_BODY.file), API_REQUEST_BODY.file.name);
        formData.append('c1', API_REQUEST_BODY.c1);
        formData.append('c2', API_REQUEST_BODY.c2);
        formData.append('c3', API_REQUEST_BODY.c3);
        formData.append('c4', API_REQUEST_BODY.c4);
        formData.append('modelID', API_REQUEST_BODY.modelID);

        let API_REQUEST_OBJ = {
            method: 'POST',
            body: formData,
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
    };

    return (
        <>
            <div id={'upload-content-div'}>
                <CustomDivider DividerTitle={'Upload your file'}/>
                <UploadDropZone/>
                <CustomDivider DividerTitle={'Match Columns'}/>
                <UploadSchemaForm/>
                <button
                    id={'analyse-upload-btn'}
                    onClick={() => handleOnClick()}
                >
                    Analyze
                </button>
            </div>
        </>
    );
}


