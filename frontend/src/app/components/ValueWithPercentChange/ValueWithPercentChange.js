import React from 'react';
import './ValueWithPercentChange.css';

export default function ValueWithPercentChange({ rawValue }) {
    return (
        <>
            <div className="value-with-percent-container">
                <div className="raw-value">{rawValue}</div>
            </div>
        </>
    );
}
