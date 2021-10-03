import React from 'react';
import SimpleCard from '../SimpleCard/SimpleCard';
import './UploadDropZone.css';
import { useDropzone } from 'react-dropzone';
import { FaFileCsv, RiFile3Fill } from 'react-icons/all';
import { Divider } from '@mui/material';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import {
    isShowingAddTrainingDataPopupState,
    isShowingUploadCSVPopupState,
    uploadedCSVFileState,
    uploadedTrainingSetFileState
} from '../../assets/AtomStore/AtomStore';

export default function UploadDropZone(props) {
    let isUploaded = false;
    const [uploadedTrainingSet, setTrainingData] = useRecoilState(uploadedTrainingSetFileState);
    const isShowingAddTrainingDataPopup = useRecoilValue(isShowingAddTrainingDataPopupState);
    const isShowingCSVPopup = useRecoilValue(isShowingUploadCSVPopupState);
    const setUploadedCSVFile = useSetRecoilState(uploadedCSVFileState);

    const pushFileArrayUp = (fileArrayObj) => {
        // console.log(`[Dropzone] File Array Obj: ${JSON.stringify(fileArrayObj[0].name)}`);
        // console.log(`[Dropzone] File Array Obj.size: ${fileArrayObj[0].size}`);
        // console.log(`[Dropzone] File Array Obj.name: ${fileArrayObj[0].name}`);
        console.log(props.isAnalyzeCSVPopupShowing);
        if (isShowingAddTrainingDataPopup) {
            setTrainingData(fileArrayObj);
            // console.log(`[Dropzone] Uploaded Training Set: ${JSON.stringify(uploadedTrainingSet)}`);
            console.log('Training Data Popup is showing');
        } else if (props.isAnalyzeCSVPopupShowing) {
            console.log('Analyse CSV NOW Popup is showing');
            setUploadedCSVFile(fileArrayObj);
        }
    };

    const {
        getRootProps,
        getInputProps,
        open,
        acceptedFiles
    } = useDropzone({
        // Disable click and keydown behavior
        noClick: true,
        noKeyboard: true,
        //update file preview on drop
        onDrop: () => {
            //pushing the file array up to parent component
            pushFileArrayUp(acceptedFiles);
            isUploaded = true;
        }
    });

    //updates file preview when file uploaded with dialog (not dropped)
    let files = acceptedFiles.map(file => (
        <div
            key={file.path}
            className={'file-preview-item'}
        >
            <FaFileCsv className={'file-preview-csv-icon'}/>
            <div className={'file-preview-name'}>{file.name}</div>
            <div className={'file-preview-size'}>
                {(file.size / (1024 * 1024)).toFixed(2)}
                MB
            </div>
        </div>
    ));

    return (
        <>
            <SimpleCard
                cardTitle={''}
                cardID={'upload-card'}
                titleOnTop
            >
                <div {...getRootProps({ className: 'dropzone' })}>
                    <input {...getInputProps()} />
                    <div
                        id={'dropzone-content-div'}
                    >
                        <RiFile3Fill id={'file-icon'}/>
                        <p
                            id={'upload-instr-p'}
                        >
                            Drag and drop your files here to start uploading.
                        </p>
                        <Divider
                            style={{ width: '50%' }}
                        >
                            OR
                        </Divider>
                        <button
                            type="button"
                            onClick={open}
                            id={'browse-files-btn'}
                        >
                            Browse Files
                        </button>
                    </div>
                </div>
                <div id={'file-preview-div'}>
                    {isUploaded ? <h4>File Preview</h4> : null}
                    {files}
                </div>
            </SimpleCard>
        </>
    );
}
