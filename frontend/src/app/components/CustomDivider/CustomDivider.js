import React from 'react';
import './CustomDivider.css';
import { Divider } from '@mui/material';

export default function CustomDivider(props) {
    return (
        <>
            <div className={'custom-divider-container'}>
                <div className={'custom-divider-title'}>{props.DividerTitle}</div>
                <Divider className={'custom-divider-line'}/>
            </div>
        </>
    );
}
