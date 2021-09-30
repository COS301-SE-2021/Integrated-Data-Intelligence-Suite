import React from 'react';
import './InputBoxWithLabel.css';

export default function InputBoxWithLabel(props) {
    return (
        <>
            <div className={'simple-input-container'}>
                <p
                    className={'simple-input-label'}
                    id={props.inputLabelID}
                >
                    {props.inputLabel}
                </p>
                <input
                    type="text"
                    id={props.inputID}
                    name={props.inputName}
                    className={'simple-input-box'}
                />
            </div>
        </>
    );
}
