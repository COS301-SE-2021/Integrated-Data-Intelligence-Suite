import React from 'react';
import { Route } from 'react-router-dom';
import Switch from 'react-bootstrap/Switch';
import SideBar from '../../components/SideBar/SideBar';
import './ManageModelsPage.css';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import ModelCard from '../../components/ModelCard/ModelCard';
import { useRecoilState, useRecoilValue } from 'recoil';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import {
    isShowingDeletePopupState,
    isShowingAddTrainingDataPopupState,
    isShowingAddModelPopupState,
    listOfDataModelsState
} from '../../assets/AtomStore/AtomStore';
import { BsCloudUpload, RiAddLine } from 'react-icons/all';
import '../../components/SimpleButton/SimpleButton.css';

export default function ManageModelsPage() {
    const [isShowingDeletePopup, toggleDeletePopup] = useRecoilState(isShowingDeletePopupState);
    const [isShowingAddTrainingDataPopup, toggleAddTrainingDataPopup] = useRecoilState(isShowingAddTrainingDataPopupState);
    const [isShowingAddModelPopup, toggleAddModelPopup] = useRecoilState(isShowingAddModelPopupState);
    const [listOfDataModels, updateListOfDataModels] = useRecoilState(listOfDataModelsState);

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
            <div>
                <div>xxxx</div>
            </div>
        </SimplePopup>
    );

    const addModelPopupComponent = (
        <SimplePopup
            closePopup={() => toggleAddModelPopup(false)}
            popupTitle={'Add Data Model'}
        >
            <div>
                <div>xxxx</div>
            </div>
        </SimplePopup>
    );

    const arrayOfModels = useRecoilValue(listOfDataModelsState);

    return (

        <>
            <Switch>
                <Route exact path="/manageModels">
                    {
                        useRecoilValue(isShowingDeletePopupState)
                            ? deletePopupComponent
                            : null
                    }

                    {
                        useRecoilValue(isShowingAddTrainingDataPopupState)
                            ? addTrainingDataPopupComponent
                            : null
                    }

                    {
                        useRecoilValue(isShowingAddModelPopupState)
                            ? addModelPopupComponent
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
                                        arrayOfModels.map((obj) => (
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
