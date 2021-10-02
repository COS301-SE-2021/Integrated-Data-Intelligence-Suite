import React from 'react';
import SimpleCard from '../SimpleCard/SimpleCard';
import './UploadDropZone.css';
import { useDropzone } from 'react-dropzone';
import { FaFileCsv, RiFile3Fill } from 'react-icons/all';
import { Divider } from '@mui/material';

export default function UploadDropZone(props) {
    let isUploaded = false;

    const pushFileArrayUp = (fileArrayObj) => {
        props.setFileArray(fileArrayObj);
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
        <div key={file.path} className={'file-preview-item'}>
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
                        <p id={'upload-instr-p'}>
                            Drag and drop your files here to start uploading.
                        </p>
                        <Divider style={{ width: '50%' }}>OR</Divider>
                        <button type="button" onClick={open} id={'browse-files-btn'}>
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
