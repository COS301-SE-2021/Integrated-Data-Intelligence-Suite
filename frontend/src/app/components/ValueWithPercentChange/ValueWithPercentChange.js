import React from 'react';
import './ValueWithPercentChange.css';
import {
    BiRightDownArrowCircle,
    BiRightTopArrowCircle,
    FiArrowDownRight,
    FiArrowUpRight
} from 'react-icons/all';

const _getRandomPercentage = () => {
    let min = 1;
    let max = 90;
    return Math.floor(Math.random() * (max - min) + min) + ' %';
};

export default function ValueWithPercentChange(props) {
    console.log(props);

    return (
        <>
            <div className={'value-with-percent-container'}>
                <div className={'raw-value'}>{props.rawValue}</div>
                <div className={'icon-with-percent-changed-by'}>
                    {props.rawValue === 'Positive' ?
                        <FiArrowUpRight className={'icon-of-percent-increasing'}/>
                        : <FiArrowDownRight className={'icon-of-percent-decreasing'}/>
                    }
                    {props.rawValue === 'Positive' ?
                        <div className={'amount-of-percent-increasing'}>{_getRandomPercentage()}</div>
                        : <div className={'amount-of-percent-decreasing'}>{_getRandomPercentage()}</div>
                    }
                </div>
            </div>
        </>
    );
}
