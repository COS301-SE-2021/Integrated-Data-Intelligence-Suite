import React, { Component } from 'react';
import './UploadDataPage.css';
import { Route, Switch } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import UploadDropZone from '../../components/UploadDropZone/UploadDropZone';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';

export default class UploadDataPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <div id={'upload-content-div'}>
                    <UploadDropZone/>
                    <UploadSchemaForm/>
                    <button>
                        Analyze
                    </button>
                </div>
            </>
        );
    }
}


