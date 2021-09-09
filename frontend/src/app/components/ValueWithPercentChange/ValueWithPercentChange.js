import React from 'react';
import './ValueWithPercentChange.css';
import {
    BiRightDownArrowCircle,
    BiRightTopArrowCircle,
    FiArrowDownRight,
    FiArrowUpRight
} from 'react-icons/all';

export default function ValueWithPercentChange(props) {
    return (
        <>
            <div className={'value-with-percent-container'}>
                <div className={'raw-value'}>{props.rawValue}</div>
                <div className={'icon-with-percent-changed-by'}>

                    {props.isIncreasing
                        ? <FiArrowUpRight className={'icon-of-percent-increasing'}/>
                        : <FiArrowDownRight className={'icon-of-percent-decreasing'}/>
                    }
                    {props.isIncreasing
                        ? <div className={'amount-of-percent-increasing'}>percentChangedBy</div>
                        : <div className={'amount-of-percent-decreasing'}>percentChangedBy</div>
                    }
                </div>
            </div>
        </>
    );
}
