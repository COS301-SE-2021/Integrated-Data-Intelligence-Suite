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
    userSelectedDefaultModelState, userSelectedDeleteModelState
} from '../../assets/AtomStore/AtomStore';
import { BsCloudUpload, RiAddLine } from 'react-icons/all';
import '../../components/SimpleButton/SimpleButton.css';

export default function ManageModelsPage() {
    const [isShowingDeletePopup, toggleDeletePopup] = useRecoilState(isShowingDeletePopupState);
    const [isShowingAddModelPopup, toggleAddModelPopup] = useRecoilState(isShowingAddModelPopupState);
    const [isShowingAddTrainingDataPopup, toggleAddTrainingDataPopup] = useRecoilState(isShowingAddTrainingDataPopupState);
    const [isShowingSetDefaultModelPopup, toggleSetDefaultModelPopup] = useRecoilState(isShowingSetDefaultModelPopupState);
    const [listOfDataModels, updateListOfDataModels] = useRecoilState(listOfDataModelsState);
    const userSelectedDefaultModel = useRecoilValue(userSelectedDefaultModelState);
    const userSelectedDeleteModel = useRecoilValue(userSelectedDeleteModelState);
    const [modelId, setModelId] = useState('');

    const handleAddModel = () => {
        // TODO function call to backend to add model to current user's liat of models
    };

    const deleteDataModel = () => {
        /*
        - Send: ID of data model that has been deleted to backend
        - backend response: updated list of data models
        */

        console.log(`User Chose this model as Default: ${userSelectedDeleteModel}`);

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

    const deletePopupComponent =
        (
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

    const addTrainingDataPopupComponent =
        (
            <SimplePopup
                closePopup={() => toggleAddTrainingDataPopup(false)}
                popupTitle={'Upload Training Data'}
            >
                <div>
                    <div>xxxx</div>
                </div>
            </SimplePopup>
        );

    const setDefaultModelPopupComponent =
        (
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
                    <button onClick={() => handleAddModel}>Add</button>
                </div>
            </div>
        </SimplePopup>
    );

    const setNewDefaultDataModel = () => {
        /*
        - make api request to backed with new default model ID
        - Backend Returns Updated List of models
        */

        console.log(`User Chose this model as Default: ${userSelectedDefaultModel}`);

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
                    <div id={'manage-models-page-container'}>
                        <SideBar currentPage={'6'}/>
                        <div id={'manage-models-page-content'}>
                            <SimpleCard
                                cardID={'manage-models-card'}
                                cardTitle={'Manage Your Models'}
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
