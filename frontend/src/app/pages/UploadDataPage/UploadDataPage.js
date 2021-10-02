import React, { Component } from 'react';
import './UploadDataPage.css';
import UploadDropZone from '../../components/UploadDropZone/UploadDropZone';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';
import CustomDivider from '../../components/CustomDivider/CustomDivider';
import { styled } from '@mui/material/styles';

class UploadDataPage extends Component {
    constructor(props) {
        super(props);
        this.state = { fileArray: '' };
        this.setFileArrayObj = this.setFileArrayObj.bind(this);
    }

    handleOnClick() {
        //1. getting value of data type selected
        let array_of_radio_btn = document.getElementsByName('upload-type-radio-btn');
        let data_type_selected;
        for (let i = 0; i < array_of_radio_btn.length; i++) {
            if (array_of_radio_btn[i].checked) {
                data_type_selected = array_of_radio_btn[i].value;
            }
        }
        console.log(`Data-type-selected: ${data_type_selected}`);

        //2. getting content inside the 4 edit boxes
        let array_4_edit_boxes = document.getElementsByName('schema-edit-box');
        let array_4_edit_box_values = [];
        for (let i = 0; i < array_4_edit_boxes.length; i++) {
            array_4_edit_box_values[i] = array_4_edit_boxes[i].value;
        }
        console.log(`array-4-edit-boxes-values: ${array_4_edit_box_values}`);

        //3. geting file array
        console.log(`file array obj double check: ${this.state.fileArray}`);

        //4. Make Post request to backend

        // const requestOptions = {
        //     method: 'POST',
        //     headers: { 'Content-Type': 'application/json' },
        //     body: JSON.stringify(obj),
        // };
        //
        // const url = `/main/ENTER_ENDPOINT_HERE`;
        // console.log(requestOptions)
        //
        // fetch(`http://localhost:9000${url}`, requestOptions)
        //     .then((response) => response.json())
        //     .then((json) => {
        //         this.setState((prevState) => ({ showLoadingIcon: false }));
        //         // remove or stop the loading icon
        //         this.handleTextChange(json);
        //     })
        //     .catch((err) => {
        //         this.setState((prevState) => ({ showLoadingIcon: false }));
        //         console.log('error while retrieving data from backend');
        //         console.log(err.message);
        //     });


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
                        onClick={() =>
                            this.handleOnClick()
                        }
                    >
                        Analyze
                    </button>
                </div>
            </>
        );
    }
}

export {UploadDataPage};



