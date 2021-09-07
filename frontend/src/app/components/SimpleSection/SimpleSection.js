import React from 'react';
import './SimpleSection.css';

export default function SimpleSection(props) {
    return (
        <>
            <div className="simple-section-container" id={props.cardID}>
                <div className="simple-section-title">{props.cardTitle}</div>
                <div className="simple-section-body">
                    {props.children}
                </div>
            </div>
        </>
    );
}
