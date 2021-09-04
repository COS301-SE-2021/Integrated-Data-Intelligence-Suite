import React from 'react';
import './ProfileCard.css';
import RowDivider from '../RowDivider/RowDivider';
import ProfileCardBanner from '../ProfileCardBanner/ProfileCardBanner';
import ProfileCardDisplayInfo from '../ProfileCardDisplayInfo/ProfileCardDisplayInfo';

export default function ProfileCard(props) {
    return (
        <>
            <div id={'profile-card-container'}>
                <div className={'profile-card-title'}>{props.cardTitle}</div>
                <div className={'profile-card-body'}>
                    <ProfileCardBanner/>
                    <ProfileCardDisplayInfo/>

                </div>
            </div>

        </>
    );
}
