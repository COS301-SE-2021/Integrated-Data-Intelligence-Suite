import React, { Component } from 'react';
import './UploadDataPage.css';
import { Route, Switch } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import UploadDropZone from '../../components/UploadDropZone/UploadDropZone';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';
import CustomDivider from '../../components/CustomDivider/CustomDivider';
import { styled } from '@mui/material/styles';

export default class UploadDataPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <div id={'upload-content-div'}>
                    <CustomDivider DividerTitle={'Upload your file'}/>
                    <UploadDropZone/>
                    <CustomDivider DividerTitle={'Upload your file'}/>
                    <UploadSchemaForm/>
                    <button>
                        Analyze
                    </button>
                </div>
            </>
        );
    }
}


