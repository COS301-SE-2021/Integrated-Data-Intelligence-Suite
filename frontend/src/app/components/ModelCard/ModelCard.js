import React, { useState } from 'react';
import './ModelCard.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import { AiOutlineShareAlt, MdDelete } from 'react-icons/all';
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import SimplePopup from '../SimplePopup/SimplePopup';
import { useRecoilState } from 'recoil';
import { isShowingDeletePopupState } from '../../assets/AtomStore/AtomStore';

export default function ModelCard(props) {
    const [isShowingDeletePopup, toggleDeletePopup] = useRecoilState(isShowingDeletePopupState);

    return (
        <>
            <div className={'model-card-container'}>
                <div className={'model-card-title-bar'}>
                    <div id={'model-card-default-tag'}>Default</div>
                    <div id={'model-card-btn-container'}>
                        <Tooltip
                            title="Delete Model"
                            arrow
                            className={'simple-tooltip'}
                            placement="top"
                        >
                            <button
                                className={'model-card-delete-btn model-card-btn'}
                                onClick={() => toggleDeletePopup(true)}
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
                            <button className={'model-card-share-btn model-card-btn'}>
                                <AiOutlineShareAlt
                                    className={'model-card-share-icon model-card-icon'}
                                />
                            </button>
                        </Tooltip>
                        <input type={'radio'}/>
                    </div>
                </div>
                <div className={'model-card-content-section'}>
                    <SimpleCard
                        titleOnTop={false}
                        cardTitle={'Model Name'}
                        extraClassName={'model-details-card'}
                    >
                        <div>Image or Details about Model</div>
                    </SimpleCard>
                </div>
            </div>
        </>
    );
}
