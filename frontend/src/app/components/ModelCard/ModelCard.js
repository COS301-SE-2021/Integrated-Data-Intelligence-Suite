import React from 'react';
import './ModelCard.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import { AiOutlineShareAlt, MdDelete } from 'react-icons/all';
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
    isShowingDeletePopupState,
    isShowingSetDefaultModelPopupState, isShowingShareModelPopupState,
    userSelectedDefaultModelState,
    userSelectedDeleteModelState, userSelectedShareModelState
} from '../../assets/AtomStore/AtomStore';

export default function ModelCard(props) {
    const toggleDeletePopup = useSetRecoilState(isShowingDeletePopupState);
    const toggleSetDefaultPopup = useSetRecoilState(isShowingSetDefaultModelPopupState);
    const setShareModelPopup = useSetRecoilState(isShowingShareModelPopupState);
    const updateUserSelectedDefaultModel = useSetRecoilState(userSelectedDefaultModelState);
    const updateUserSelectedDeleteModel = useSetRecoilState(userSelectedDeleteModelState);
    const updateUserSelectedShareModel = useSetRecoilState(userSelectedShareModelState);
    const clickedRadioButton = (e) => {
        updateUserSelectedDefaultModel(e.target.id);
        toggleSetDefaultPopup(true);
    };

    const clickedDeleteButton = (e) => {
        updateUserSelectedDeleteModel(e.target.id);
        toggleDeletePopup(true);
    };

    const clickedShareModelButton = (e) => {
        console.log(e);
        console.log(`[Share Model] ID of item clicked: ${e.target.id}`);
        updateUserSelectedShareModel(e.target.id);
        setShareModelPopup(true);
    };

    return (
        <>
            <div className={'model-card-container'}>
                <div className={'model-card-title-bar'}>
                    {props.isModelDefault
                        ? <div id={'model-card-default-tag'}>Default</div>
                        : null
                    }
                    <div id={'model-card-btn-container'}>
                        <Tooltip
                            title="Delete Model"
                            arrow
                            className={'simple-tooltip'}
                            placement="top"
                        >
                            <button
                                className={'model-card-delete-btn model-card-btn'}
                                onClick={(event) => clickedDeleteButton(event)}
                                id={props.modelID}
                            >
                                <MdDelete className={'model-card-delete-icon model-card-icon'}/>
                            </button>
                        </Tooltip>

                        <Tooltip
                            title="Share Model"
                            arrow
                            className={'simple-tooltip'}
                            placement="top"
                        >
                            <button
                                className={'model-card-share-btn model-card-btn'}
                                id={props.modelID}
                                onClick={(event) => clickedShareModelButton(event)}
                            >
                                <AiOutlineShareAlt
                                    className={'model-card-share-icon model-card-icon'}
                                    id={props.modelID}
                                />
                            </button>
                        </Tooltip>

                        <Tooltip
                            title={'Make Default Data Model'}
                            arrow
                            className={'simple-tooltip'}
                            placement={'top'}
                        >
                            <input
                                type={'radio'}
                                name="default"
                                id={props.modelID}
                                checked={props.isModelDefault}
                                onClick={(event) => clickedRadioButton(event)}
                            />
                        </Tooltip>
                    </div>
                </div>
                <div className={'model-card-content-section'}>
                    <SimpleCard
                        titleOnTop={false}
                        cardID={props.modelID}
                        cardTitle={props.modelName}
                        extraClassName={'model-details-card'}
                    >
                        <div className={'model-image'}>Image</div>
                        <span className={'model-extra-details-div'}>Details About Model</span>
                    </SimpleCard>
                </div>
            </div>
        </>
    );
}
