import React, { useState } from 'react';
import { Route } from 'react-router-dom';
import Switch from 'react-bootstrap/Switch';
import SideBar from '../../components/SideBar/SideBar';
import './ManageModelsPage.css';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import ModelCard from '../../components/ModelCard/ModelCard';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import {
    isShowingDeletePopupState,
    isShowingAddTrainingDataPopupState,
    isShowingAddModelPopupState,
    listOfDataModelsState,
    isShowingSetDefaultModelPopupState,
    userSelectedDefaultModelState,
    userSelectedDeleteModelState,
    uploadedTrainingSetFileState, isShowingShareModelPopupState, userSelectedShareModelState
} from '../../assets/AtomStore/AtomStore';
import { BsCloudUpload, IoCopyOutline, RiAddLine } from 'react-icons/all';
import '../../components/SimpleButton/SimpleButton.css';
import CustomDivider from '../../components/CustomDivider/CustomDivider';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';
import UploadDropZone from '../../components/UploadDropZone/UploadDropZone';
import InputBoxWithLabel from '../../components/InputBoxWithLabel/InputBoxWithLabel';
import { Tooltip } from '@mui/material';

export default function ManageModelsPage() {
    const [isShowingDeletePopup, toggleDeletePopup] = useRecoilState(isShowingDeletePopupState);
    const [isShowingAddModelPopup, toggleAddModelPopup] = useRecoilState(isShowingAddModelPopupState);
    const [isShowingAddTrainingDataPopup, toggleAddTrainingDataPopup] = useRecoilState(isShowingAddTrainingDataPopupState);
    const [isShowingSetDefaultModelPopup, toggleSetDefaultModelPopup] = useRecoilState(isShowingSetDefaultModelPopupState);
    const [isShowingShareModelPopup, toggleShareModelPopup] = useRecoilState(isShowingShareModelPopupState);
    const [listOfDataModels, updateListOfDataModels] = useRecoilState(listOfDataModelsState);
    const userSelectedDefaultModel = useRecoilValue(userSelectedDefaultModelState);
    const userSelectedDeleteModel = useRecoilValue(userSelectedDeleteModelState);
    const userSelectedShareModel = useRecoilValue(userSelectedShareModelState);
    const uploadedTrainingDataFileArrayObj = useRecoilValue(uploadedTrainingSetFileState);
    const [modelId, setModelId] = useState('');

    const handleAddModel = () => {
        console.log(`Model Id Entered in Add: ${modelId}`);

        /*
        -  API_REQUEST_OBJ: new id model to add
        -  API_RESPONSE_OBJ: updated list of data models
         */
        let url = '';
        let API_REQUEST_BODY = {
            modelID: modelId,
        };
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
                // updateListOfDataModels(API_RESPONSE_OBJ);

            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
            });

        //update data model with data model from backend
        updateListOfDataModels([{
            modelID: 'm1',
            modelName: 'Itachi',
            isModelDefault: false
        }, {
            modelID: 'm2',
            modelName: 'Sasuke',
            isModelDefault: false
        }, {
            modelID: 'm3',
            modelName: 'Zabuza',
            isModelDefault: true
        }, {
            modelID: 'm4',
            modelName: 'Shisui',
            isModelDefault: false
        }, {
            modelID: 'm5',
            modelName: 'Naruto'
        }
        ]);

        //close popup
        toggleAddModelPopup(true);

    };
    const deleteDataModel = () => {
        console.log(`User Chose this model as Default: ${userSelectedDeleteModel}`);

        /*
        - API_REQUEST_BODY: ID of data model that has been deleted to backend
        - API_RESPONSE_OBJ: updated list of data models
        */
        let url = '';
        let API_REQUEST_BODY = {
            modelID: userSelectedDeleteModel,
        };
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
                // updateListOfDataModels(API_RESPONSE_OBJ);
            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
            });

        //update List of data models with values from backend
        updateListOfDataModels(
            [{
                modelID: 'm1',
                modelName: 'Itachi',
                isModelDefault: true
            }, {
                modelID: 'm2',
                modelName: 'Sasuke',
                isModelDefault: false
            }, {
                modelID: 'm4',
                modelName: 'Shisui',
                isModelDefault: false
            }]
        );

        //Close the popup
        toggleDeletePopup(false);
    };
    const setNewDefaultDataModel = () => {
        console.log(`User Chose this model as Default: ${userSelectedDefaultModel}`);

        /*
        - API_REQUEST_BODY: ID of data model that has been deleted to backend
        - API_RESPONSE_OBJ: updated list of data models
        */
        let url = '';
        let API_REQUEST_BODY = {
            modelID: userSelectedDefaultModel,
        };
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
                // updateListOfDataModels(API_RESPONSE_OBJ);
            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
            });

        //update List of data models with values from backend
        updateListOfDataModels(
            [{
                modelID: 'm1',
                modelName: 'Itachi',
                isModelDefault: true
            }, {
                modelID: 'm2',
                modelName: 'Sasuke',
                isModelDefault: false
            }, {
                modelID: 'm3',
                modelName: 'Zabuza',
                isModelDefault: false
            }, {
                modelID: 'm4',
                modelName: 'Shisui',
                isModelDefault: false
            }]
        );

        //Close the popup
        toggleSetDefaultModelPopup(false);
    };
    const handleUploadedTrainingData = () => {
        console.log(`[Uploaded training data set]: ${uploadedTrainingDataFileArrayObj}`);
        console.log(`[uploading training data set]: ${JSON.stringify(uploadedTrainingDataFileArrayObj)}`);

        //fetch values of input boxes
        let model_name = document.getElementById('input-training-model-name').value;
        let date = document.getElementById('input-training-date').value;
        let interaction = document.getElementById('input-training-interaction').value;
        let text = document.getElementById('input-training-text').value;
        let location = document.getElementById('input-training-location').value;
        let isTrending = document.getElementById('input-training-isTrending').value;

        /*
          - API_REQUEST_BODY: ID of data model that has been deleted to backend
          - API_RESPONSE_OBJ: updated list of data models
        */
        let url = '/trainUpload';
        let API_REQUEST_BODY_TRAIN = {
            file: uploadedTrainingDataFileArrayObj,
            c1: text,
            c2: location,
            c3: interaction,
            c4: date,
            c5: isTrending,
            modelName: model_name
        };
        console.log(API_REQUEST_BODY_TRAIN);

        const formData = new FormData();
        formData.append('file', new Blob(API_REQUEST_BODY_TRAIN.file), 'trainingSet.csv');
        formData.append('c1', API_REQUEST_BODY_TRAIN.c1);
        formData.append('c2', API_REQUEST_BODY_TRAIN.c2);
        formData.append('c3', API_REQUEST_BODY_TRAIN.c3);
        formData.append('c4', API_REQUEST_BODY_TRAIN.c4);
        formData.append('c5', API_REQUEST_BODY_TRAIN.c5);
        formData.append('modelName', API_REQUEST_BODY_TRAIN.modelID);

        const API_REQUEST_OBJ_TRAIN = {
            method: 'POST',
            body: formData,
        };

        let API_RESPONSE_OBJ = null;
        fetch(`http://localhost:9000${url}`, API_REQUEST_OBJ_TRAIN)
            .then((response) => response.json())
            .then((json) => {
                API_RESPONSE_OBJ = json;
                this.handleTextChange(API_RESPONSE_OBJ);
            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
            });

        //Update List of data Models
        updateListOfDataModels(
            [{
                modelID: 'm1',
                modelName: 'Itachi',
                isModelDefault: true
            }, {
                modelID: 'm2',
                modelName: 'Sasuke',
                isModelDefault: false
            }, {
                modelID: 'm3',
                modelName: 'Zabuza',
                isModelDefault: false
            }, {
                modelID: 'm4',
                modelName: 'Shisui',
                isModelDefault: false
            }, {
                modelID: 'm5',
                modelName: 'Hinata',
                isModelDefault: false
            }]
        );
    };

    const deletePopupComponent = (
        <SimplePopup
            closePopup={() => toggleDeletePopup(false)}
            popupTitle={'Delete Model'}
            popupID={'delete-model-popup'}
        >
            <div id={'delete-model-popup-msg'}>Are you sure you want to delete this modal?</div>
            <div id={'delete-model-popup-btn-container'}>
                <button
                    id={'delete-model-popup-btn-yes'}
                    onClick={() => deleteDataModel()}
                >
                    Yes
                </button>
                <button
                    id={'delete-model-popup-btn-no'}
                    onClick={() => toggleDeletePopup(false)}
                >
                    No
                </button>
            </div>
        </SimplePopup>
    );

    const addTrainingDataPopupComponent = (
        <SimplePopup
            closePopup={() => toggleAddTrainingDataPopup(false)}
            popupTitle={'Upload Training Data'}
        >
            <div id={'upload-content-div'}>
                <CustomDivider DividerTitle={'Upload your file'}/>
                <UploadDropZone/>
                <CustomDivider DividerTitle={'Match Columns'}/>
                <div id={'upload-training-data-form'}>
                    <InputBoxWithLabel
                        inputLabel={'Model Name'}
                        inputID={'input-training-model-name'}
                    />
                    <InputBoxWithLabel
                        inputLabel={'Date'}
                        inputID={'input-training-date'}
                    />
                    <InputBoxWithLabel
                        inputLabel={'Interaction'}
                        inputID={'input-training-interaction'}
                    />
                    <InputBoxWithLabel
                        inputLabel={'Text'}
                        inputID={'input-training-text'}
                    />
                    <InputBoxWithLabel
                        inputLabel={'Location'}
                        inputID={'input-training-location'}
                    />
                    <InputBoxWithLabel
                        inputLabel={'isTrending'}
                        inputID={'input-training-isTrending'}
                    />
                </div>

                <button
                    type={'button'}
                    id={'upload-training-data-btn'}
                    onClick={() => handleUploadedTrainingData()}
                    className={'simple-btn'}
                >
                    Upload Training Data
                </button>
            </div>
        </SimplePopup>
    );

    const setDefaultModelPopupComponent = (
        <SimplePopup
            closePopup={() => toggleSetDefaultModelPopup(false)}
            popupTitle={'Set Default'}
        >
            <div id={'delete-model-popup-msg'}>
                Do you want to make this model your default data model?
            </div>
            <div id={'delete-model-popup-btn-container'}>
                <button
                    id={'delete-model-popup-btn-yes'}
                    onClick={() => setNewDefaultDataModel()}
                >
                    Yes
                </button>
                <button
                    id={'delete-model-popup-btn-no'}
                    onClick={() => toggleSetDefaultModelPopup(false)}
                >
                    No
                </button>
            </div>
        </SimplePopup>
    );

    const addModelPopupComponent = (
        <SimplePopup
            closePopup={() => toggleAddModelPopup(false)}
            popupTitle="Add Data Model"
        >
            <div className="add-model-container">
                <div className="input-container">
                    <div className="label">ID</div>
                    <input
                        type="text"
                        id="modelIdInput"
                        placeholder="model id"
                        value={modelId}
                        onChange={(event) => setModelId(event.currentTarget.value)}
                    />
                </div>
                <div className="button-container">
                    <button
                        onClick={() => handleAddModel()}
                        className={'simple-btn'}
                        id={'add-model-btn'}
                    >
                        Add Model
                    </button>
                </div>
            </div>
        </SimplePopup>
    );

    const shareModelPopupComponent = (
        <SimplePopup
            closePopup={() => toggleShareModelPopup(false)}
            popupTitle={'Share Model'}
        >
            <div id={'share-model-container'}>
                <div id={'share-model-id-icon'}>ID</div>
                <div
                    id={'share-model-id-value'}
                >
                    {userSelectedShareModel}
                </div>
                <Tooltip
                    title={'Copy ID'}
                >
                    <button
                        id={'share-model-copy-btn'}
                    >
                        <IoCopyOutline id={'share-model-copy-icon'}/>
                    </button>
                </Tooltip>
            </div>
        </SimplePopup>
    );

    return (
        <>
            <Switch>
                <Route exact path="/manageModels">
                    {
                        isShowingDeletePopup
                            ? deletePopupComponent
                            : null
                    }
                    {
                        isShowingAddTrainingDataPopup
                            ? addTrainingDataPopupComponent
                            : null
                    }
                    {
                        isShowingAddModelPopup
                            ? addModelPopupComponent
                            : null
                    }
                    {
                        isShowingSetDefaultModelPopup
                            ? setDefaultModelPopupComponent
                            : null
                    }
                    {
                        isShowingShareModelPopup
                            ? shareModelPopupComponent
                            : null
                    }
                    <div id={'manage-models-page-container'}>
                        <SideBar currentPage={'6'}/>
                        <div id={'manage-models-page-content'}>
                            <SimpleCard
                                cardID={'manage-models-card'}
                                cardTitle={'Manage Your Data Models'}
                                titleOnTop
                            >
                                <div id={'manage-models-btn-row'}>
                                    <button
                                        className={'simple-btn simple-btn-hover'}
                                        onClick={() => toggleAddTrainingDataPopup(true)}
                                    >
                                        <BsCloudUpload
                                            className={'simple-btn-icon simple-btn-hover'}
                                        />
                                        Upload Training Data
                                    </button>

                                    <button
                                        className={'simple-btn simple-btn-hover'}
                                        onClick={() => toggleAddModelPopup(true)}
                                    >
                                        <RiAddLine className={'simple-btn-icon simple-btn-hover'}/>
                                        Add Model
                                    </button>
                                </div>

                                <div id={'manage-models-card-row'}>
                                    {
                                        listOfDataModels.map((obj) => (
                                            <ModelCard
                                                modelID={obj.modelID}
                                                modelName={obj.modelName}
                                                isModelDefault={obj.isModelDefault}
                                                key={obj.modelID}
                                            />
                                        ))
                                    }
                                </div>
                            </SimpleCard>
                        </div>
                    </div>
                </Route>
            </Switch>
        </>
    );
}
