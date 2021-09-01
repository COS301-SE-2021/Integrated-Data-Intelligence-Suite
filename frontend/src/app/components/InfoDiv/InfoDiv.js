import React from 'react';
import './InfoDiv.css';

function InfoDiv(props) {

    return (
        <>
            <div
                id={'info-div-container'}
            >
                <div
                    className={'info-div'}
                    id={'info-div-start'}
                >
                    Top 5 countries account for
                </div>
                <p id={'info-value'}>{props.infoValue}</p>
                <div
                    className={'info-div'}
                    id={'info-div-end'}
                >
                    of total engagement
                </div>
            </div>
        </>
    );
}

export default InfoDiv;
