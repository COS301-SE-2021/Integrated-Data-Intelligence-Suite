import React from 'react';
import './ProfilePage.css';
import ProfileCard from '../../components/ProfileCard/ProfileCard';

export default function ProfilePage(props) {

    return (
        <>
            <div id={'profile-page-container'}>
                <ProfileCard/>

                <div style={{ border: '3px solid red' }}>
                    Password and Authentication
                </div>

                <div style={{ border: '3px solid red' }}>
                    another section
                </div>

                <div style={{ border: '3px solid red' }}>
                    another section
                </div>

            </div>

        </>

    );
}
