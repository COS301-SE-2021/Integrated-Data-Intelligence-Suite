import React from 'react';
import { Route } from 'react-router-dom';
import Switch from 'react-bootstrap/Switch';
import SideBar from '../../components/SideBar/SideBar';
import './ManageModelsPage.css';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import ModelCard from '../../components/ModelCard/ModelCard';
import { atom, useRecoilState, useRecoilValue } from 'recoil';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import {
    isShowingDeletePopupState,
    isShowingAddTrainingDataPopupState,
    isShowingAddModelPopupState
} from '../../assets/AtomStore/AtomStore';
import { BsCloudUpload, GrAdd, RiAddLine } from 'react-icons/all';
import '../../components/SimpleButton/SimpleButton.css';

export default function ManageModelsPage(props) {

    const [isShowingDeletePopup, toggleDeletePopup] = useRecoilState(isShowingDeletePopupState);
    const [isShowingAddTrainingDataPopup, toggleAddTrainingDataPopup] = useRecoilState(isShowingAddTrainingDataPopupState);
    const [isShowingAddModelPopup, toggleAddModelPopup] = useRecoilState(isShowingAddModelPopupState);
    const deletePopupComponent =
        (
            <SimplePopup
                closePopup={() => toggleDeletePopup(false)}
                popupTitle={'Delete Model'}
            >
                <div>
                    <div>Are you sure you want to delete this modal?</div>
                    <div>
                        <button>Yes</button>
                        <button>No</button>
                    </div>
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
                                        className={'simple-btn'}
                                        onClick={() => toggleAddTrainingDataPopup(true)}
                                    >
                                        <BsCloudUpload className={'simple-btn-icon'}/>
                                        Upload Training Data
                                    </button>

                                    <button
                                        className={'simple-btn'}
                                        onClick={() => toggleAddModelPopup(true)}
                                    >
                                        <RiAddLine className={'simple-btn-icon'}/>
                                        Add Model
                                    </button>
                                </div>
                                <div id={'manage-models-card-row'}>
                                    <ModelCard/>
                                    <ModelCard/>
                                    <ModelCard/>
                                    <ModelCard/>
                                    <ModelCard/>
                                    <ModelCard/>
                                </div>
                            </SimpleCard>
                        </div>
                    </div>
                </Route>
            </Switch>
        </>
    );
}
