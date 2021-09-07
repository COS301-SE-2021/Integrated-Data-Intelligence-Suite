import React from 'react';
import './PersonalInfoDiv.css';
import { AiOutlineEdit } from 'react-icons/all';

export default function PersonalInfoDiv(props) {
    return (
        <>
            <div id="personal-info-container">
                <div className="personal-info-title">{props.infoTitle}</div>
                <div className="personal-info-content">{props.infoContent}</div>
                <div className="personal-info-reveal">Reveal</div>
                <div className="personal-info-edit-btn">
                    <AiOutlineEdit className="profile-page-edit-icon" />
                </div>
            </div>
        </>
    );
}
