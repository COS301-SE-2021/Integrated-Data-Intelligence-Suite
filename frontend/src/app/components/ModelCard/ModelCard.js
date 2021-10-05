import React, { useEffect } from 'react';
import './ModelCard.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import { AiFillHeart, AiOutlineHeart, AiOutlineShareAlt, MdDelete } from 'react-icons/all';
import Tooltip from '@mui/material/Tooltip';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
    isShowingDeletePopupState,
    isShowingSetDefaultModelPopupState,
    isShowingShareModelPopupState,
    userSelectedDefaultModelState,
    userSelectedDeleteModelState,
    userSelectedShareModelState
} from '../../assets/AtomStore/AtomStore';

export default function ModelCard(props) {
    const setShowDefaultPopup = useSetRecoilState(isShowingSetDefaultModelPopupState);
    const setShareModelPopup = useSetRecoilState(isShowingShareModelPopupState);
    const setShowDeleteModelPopup = useSetRecoilState(isShowingDeletePopupState);
    const [userSelectedDefaultModel, updateUserSelectedDefaultModel] = useRecoilState(userSelectedDefaultModelState);
    const [userSelectedDeleteModel, updateUserSelectedDeleteModel] = useRecoilState(userSelectedDeleteModelState);
    const [userSelectedShareModel, updateUserSelectedShareModel] = useRecoilState(userSelectedShareModelState);

    /*
    * DELETE
    */
    const clickedDeleteButton = (modelID) => {
        updateUserSelectedDeleteModel(modelID);
    };
    useEffect(() => {
        if (userSelectedDeleteModel === null) {
            setShowDeleteModelPopup(false);
        } else {
            setShowDeleteModelPopup(true);
        }
    }, [userSelectedDeleteModel]);

    /*
    * SHARE
    */
    const clickedShareModelButton = (modelID) => {
        updateUserSelectedShareModel(modelID);
    };
    useEffect(() => {
        if (userSelectedShareModel === null) {
            setShareModelPopup(false);
        } else {
            setShareModelPopup(true);
        }
    }, [userSelectedShareModel]);

    /*
    * SET DEFAULT
    */
    const clickedRadioButton = (modelID) => {
        updateUserSelectedDefaultModel(modelID);
    };
    useEffect(() => {
        if (userSelectedDefaultModel === null) {
            setShowDefaultPopup(false);
        } else {
            setShowDefaultPopup(true);
        }
    }, [userSelectedDefaultModel]);

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
                                onClick={() => clickedDeleteButton(props.modelID)}
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
                                onClick={() => clickedShareModelButton(props.modelID)}
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
                            <button
                                type={'radio'}
                                name="default"
                                id={props.modelID}
                                // checked={props.isModelDefault}
                                onClick={() => clickedRadioButton(props.modelID)}
                                className={'select-default-btn'}
                            >
                                {
                                    props.isModelDefault
                                        ? (
                                            <AiFillHeart
                                                id={props.modelID}
                                                className={'heart-icon-filled'}
                                            />
                                        )
                                        : (
                                            <AiOutlineHeart
                                                id={props.modelID}
                                                className={'heart-icon-not-filled'}
                                            />
                                        )
                                }
                            </button>

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
                        <span className={'model-extra-details-div'}>{`Accuracy: ${props.modelAccuracy}`}</span>
                    </SimpleCard>
                </div>
            </div>
        </>
    );
}
