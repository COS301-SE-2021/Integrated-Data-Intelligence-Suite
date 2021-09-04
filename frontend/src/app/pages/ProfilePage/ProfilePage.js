import React from 'react';
import './ProfilePage.css';
import ProfileCard from '../../components/ProfileCard/ProfileCard';
import RowDivider from '../../components/Divider/RowDivider';

export default function ProfilePage(props) {

    return (
        <>
            <div id={'profile-page-container'}>
                <ProfileCard
                    style={{
                        gridRowStart: 1,
                        gridRowEnd: 2
                    }}
                    cardTitle={'My Profile'}
                />
                <RowDivider
                    style={{
                        gridRowStart: 2,
                        gridRowEnd: 3
                    }}
                />

                <ProfileCard
                    style={{
                        gridRowStart: 3,
                        gridRowEnd: 4
                    }}
                    cardTitle={'Password and Authentication'}
                />
                <RowDivider/>

            </div>
        </>
    );
}
