import React from 'react';
import PersonalInfoDiv from '../PersonalInfoDiv/PersonalInfoDiv';
import './PersonalInfoCard.css';

export default function PersonalInfoCard(props) {
    return (
        <>
            <div id="personal-info-card-container">
                <PersonalInfoDiv
                  id="email-info-div"
                  infoTitle="EMAIL"
                  infoContent="KanyeWest@emerge.com"
                />
                <PersonalInfoDiv
                  id="phone-number-info-div"
                  infoTitle="PHONE NUMBER"
                  infoContent="515-8008-312"

                />
                <PersonalInfoDiv
                  id="account-type-info-div"
                  infoTitle="ACCOUNT TYPE"
                  infoContent="Admin"
                />
            </div>
        </>
    );
}
