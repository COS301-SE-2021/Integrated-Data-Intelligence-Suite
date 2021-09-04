import React from 'react';
import PersonalInfoDiv from '../PersonalInfoDiv/PersonalInfoDiv';
import './PersonalInfoCard.css';

export default function PersonalInfoCard(props) {

    return (
        <>
            <div id={'personal-info-card-container'}>
                <PersonalInfoDiv
                    id={'email-info-div'}
                    infoTitle={'Email'}
                    infoContent={'KanyeWest@emerge.com'}
                />
                <PersonalInfoDiv
                    id={'phone-number-info-div'}
                    infoTitle={'Phone Number'}
                    infoContent={'515-8008-312'}

                />
                <PersonalInfoDiv
                    id={'account-type-info-div'}
                    infoTitle={'Account Type'}
                    infoContent={'Admin'}
                />
            </div>
        </>
    );
}
