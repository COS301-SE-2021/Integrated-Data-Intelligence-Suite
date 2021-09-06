import React from 'react';
import './SimpleCard.css';

export default function SimpleCard(props) {
    return (
        <>
            <div className={'simple-card-container'} id={props.cardID}>
                <div className={'simple-card-title'}>{props.cardTitle}</div>
                <div className={'simple-card-body'}>
                    {props.children}
                </div>
            </div>
        </>
    );
}
