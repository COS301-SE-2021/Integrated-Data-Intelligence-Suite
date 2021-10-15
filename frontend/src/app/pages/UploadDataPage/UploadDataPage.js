import React, { Component } from 'react';
import './UploadDataPage.css';
import { useRecoilValue } from 'recoil';
import UploadDropZone from '../../components/UploadDropZone/UploadDropZone';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';
import CustomDivider from '../../components/CustomDivider/CustomDivider';
import { uploadedCSVFileState } from '../../assets/AtomStore/AtomStore';

export default function UploadDataPage(props) {
    const uploadedAnalysingCSVFile = useRecoilValue(uploadedCSVFileState);
    const handleOnClick = () => {
        // getting content inside the 4 edit boxes
        const text_message = document.getElementById('upload-input-text-msg').value;
        const location = document.getElementById('upload-input-location').value;
        const likes = document.getElementById('upload-input-likes').value;
        const date = document.getElementById('upload-input-date').value;

        // Make Post request to backend
        /*
           -  API_REQUEST_OBJ: input box values, file array
           -  API_RESPONSE_OBJ: same as search
        */
        const url = '/analyzeUpload';
        const API_REQUEST_BODY = {
            file: uploadedAnalysingCSVFile,
            c1: text_message,
            c2: location,
            c3: likes,
            c4: date,
            modelID: 'Default',
        };

        const formData = new FormData();
        // formData.append('file', API_REQUEST_BODY.file);
        formData.append('file', new Blob(API_REQUEST_BODY.file), API_REQUEST_BODY.file[0].name);
        formData.append('c1', API_REQUEST_BODY.c1);
        formData.append('c2', API_REQUEST_BODY.c2);
        formData.append('c3', API_REQUEST_BODY.c3);
        formData.append('c4', API_REQUEST_BODY.c4);
        formData.append('modelID', API_REQUEST_BODY.modelID);

        const API_REQUEST_OBJ = {
            method: 'POST',
            body: formData,
        };

        let API_RESPONSE_OBJ = null;
        fetch(`http://localhost:9000${url}`, API_REQUEST_OBJ)
            .then((response) => response.json())
            .then((json) => {
                API_RESPONSE_OBJ = json;
                props.handleTextChange(API_RESPONSE_OBJ);
            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
            });
    };

    return (
        <>
            <div id="upload-content-div">
                <CustomDivider DividerTitle="Upload your file" />
                <UploadDropZone
                  isAnalyzeCSVPopupShowing={props.isAnalyzeCSVPopupShowing}
                />
                <CustomDivider DividerTitle="Match Columns" />
                <UploadSchemaForm />
                <button
                  id="analyse-upload-btn"
                  onClick={() => handleOnClick()}
                >
                    Analyze
                </button>
            </div>
        </>
    );
}
