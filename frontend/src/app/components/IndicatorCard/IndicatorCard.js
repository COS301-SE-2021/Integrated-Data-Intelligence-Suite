import React, { useEffect, useRef, useState } from 'react';
import { Card } from 'antd';
import './IndicatorCard.css';
import { ArrowUpOutlined } from '@ant-design/icons';

function IndicatorCard(props) {

    return (
        <>
            <Card
                title={props.indicatorTitle}
                // extra={<p>14%</p>}
                className={'indicator-card'}
            >
                <div className={'indicator-value-container'}>
                    {props.indicatorValue}
                </div>

                <div className={'amount-changed-container'}>
                    <div className={'arrow-icon-container'}>
                        <ArrowUpOutlined/>
                    </div>
                    <div className={'percentage-value-container'}>
                        +10%
                    </div>
                </div>
            </Card>

        </>
    );
}

export default IndicatorCard;
