import React from 'react';
import { GoVerified } from 'react-icons/all';
import './ProfileCardDisplayInfo.css';

export default function ProfileCardDisplayInfo(props) {
    return (
        <>
            <div id={'profile-card-display-info-container'}>
                <div id={'profile-card-image'}/>
                <div id={'profile-card-name'}>Kanye West</div>
                <GoVerified className={'verified-icon'}/>
            </div>
        </>
    );

}
