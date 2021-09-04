import React from 'react';
import './ProfileCard.css';
import RowDivider from '../Divider/RowDivider';

export default function ProfileCard(props) {
    return (
        <>
            <div id={'profile-card-container'}>
                <div className={'profile-card-title'}>{props.cardTitle}</div>
                <div className={'profile-card-main-section'}>main section</div>
            </div>

        </>
    );
}
