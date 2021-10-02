import React from 'react';
import './ModelCard.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import { AiOutlineShareAlt, MdDelete } from 'react-icons/all';

export default function ModelCard(props) {

    return (
        <>
            <div className={'model-card-container'}>
                <div className={'model-card-title-bar'}>
                    <div id={'model-card-default-tag'}>Default</div>
                    <div id={'model-card-btn-container'}>

                        <button className={'model-card-delete-btn model-card-btn'}>
                            <MdDelete className={'model-card-delete-icon model-card-icon'}/>
                        </button>

                        <button className={'model-card-share-btn model-card-btn'}>
                            <AiOutlineShareAlt className={'model-card-share-icon model-card-icon'}/>
                        </button>

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
