import React from 'react';
import './SimpleCard.css';

export default function SimpleCard(props) {
    return (
        <>
            {
                props.titleOnTop
                    ? (
                        <div
                            className={`simple-card-container ${props.extraClassName}`}
                            id={props.cardID}
                            style={{
                                display: 'grid',
                                gridTemplateColumns: '100%',
                                gridTemplateRows: '3em auto',
                                height: '100%',
                                marginBottom: '3%'
                            }}
                        >
                            <div className="simple-card-title">{props.cardTitle}</div>
                            <div className="simple-card-body">{props.children}</div>
                        </div>
                    )
                    : (
                        <div
                            className={`simple-card-container ${props.extraClassName}`}
                            id={props.cardID}
                            style={{
                                display: 'grid',
                                gridTemplateColumns: '100%',
                                gridTemplateRows: 'auto 3em',
                                height: '100%',
                                marginBottom: '3%'
                            }}
                        >
                            <div className="simple-card-body">{props.children}</div>
                            <div className="simple-card-title">{props.cardTitle}</div>
                        </div>
                    )
            }
        </>
    );
}
