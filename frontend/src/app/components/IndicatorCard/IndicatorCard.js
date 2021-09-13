import React, { useEffect, useRef, useState } from 'react';
import { Card } from 'antd';
import { ArrowUpOutlined } from '@ant-design/icons';
import './IndicatorCard.css';

function IndicatorCard(props) {
    return (
        <>
            <Card
              title={props.indicatorTitle}
              className="indicator-card"
              id={props.cardID}
            >
                <div className="indicator-content-container">
                    <div className="indicator-value-container">
                        {props.indicatorValue}
                    </div>

                    <div className="amount-changed-container">
                        <div className="arrow-icon-container">
                            {props.showArrow ? <ArrowUpOutlined /> : null}
                        </div>
                        <div className="percentage-value-container">
                            {props.percentChange}
                        </div>
                    </div>
                </div>
                {props.graphComponent}
            </Card>
        </>
    );
}

export default IndicatorCard;
