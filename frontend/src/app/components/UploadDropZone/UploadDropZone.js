import React from 'react';
import SimpleCard from '../SimpleCard/SimpleCard';
import './UploadDropZone.css';
import { useDropzone } from 'react-dropzone';
import { RiFile3Fill } from 'react-icons/all';
import { Divider } from '@mui/material';
import { styled } from '@mui/material/styles';
import CustomDivider from '../CustomDivider/CustomDivider';

export default function UploadDropZone() {
    const {
        getRootProps,
        getInputProps,
        open,
        acceptedFiles
    } = useDropzone({
        // Disable click and keydown behavior
        noClick: true,
        noKeyboard: true
    });

    const files = acceptedFiles.map(file => (
        <li key={file.path}>
            {file.name}
            -
            {file.size}
            bytes
        </li>
    ));

    return (
        <>
            <SimpleCard
                cardTitle={''}
                cardID={'upload-card'}
                titleOnTop
            >

                <CustomDivider DividerTitle={'Upload your file'}/>

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
                <div>
                    <h4>Files</h4>
                    <ul>{files}</ul>
                </div>
            </SimpleCard>

        </>
    );
}
