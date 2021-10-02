import React from 'react';
import { GrClose } from 'react-icons/all';
import './SimplePopup.css';

export default function SimplePopup(props) {
    const {
        closePopup,
        children,
    } = props;
    return (
        <>
            <div className="popup-container">
                <div className="popup-div">
                    <div className="popup-title-bar">
                        <div className="popup-title">
                            Some Title
                        </div>
                        <GrClose className="clickable" style={{ fontSize: '30px' }} onClick={closePopup} />
                    </div>
                    <div className="popup-body">
                        {children}
                    </div>
                </div>
            </div>
        </>
    );
}
