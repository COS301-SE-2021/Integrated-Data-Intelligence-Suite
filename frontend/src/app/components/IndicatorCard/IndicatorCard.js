import React, { useEffect, useRef, useState } from 'react';
import { Card } from 'antd';
import './IndicatorCard.css';
import { ArrowUpOutlined } from '@ant-design/icons';

function IndicatorCard(props) {

    return (
        <>
            <Card
                title={props.indicatorTitle}
                className={'indicator-card'}
            >

                <div className={'indicator-content-container'}>
                    <div className={'indicator-value-container'}>
                        {props.indicatorValue}
                    </div>

                    <div className={'amount-changed-container'}>
                        <div className={'arrow-icon-container'}>
                            <ArrowUpOutlined/>
                        </div>
                        <div className={'percentage-value-container'}>
                            +69%
                        </div>
                    </div>
                </div>

                <div className={'graph-container'}>
                    Line Graph here
                </div>
            </Card>

        </>
    );
}

export default IndicatorCard;
