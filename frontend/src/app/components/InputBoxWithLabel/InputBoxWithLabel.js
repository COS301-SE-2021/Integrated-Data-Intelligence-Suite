import React from 'react';
import './InputBoxWithLabel.css';

export default function InputBoxWithLabel(props) {
    return (
        <>
            <div className={'simple-input-container'}>
                <p
                    id={props.inputLabelID}
                    className={'simple-input-label'}
                >
                    {props.inputLabel}
                </p>
                <input
                    type="text"
                    id={props.inputID}
                    name={props.inputName}
                    value={props.inputValue}
                    placeholder={props.placeholder}
                    className={'simple-input-box'}
                />
            </div>
        </>
    );
}
