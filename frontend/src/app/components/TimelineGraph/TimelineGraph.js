import React from 'react';
import './TimelineGraph.css';
import { Chrono } from 'react-chrono';
import { useRecoilValue } from 'recoil';
import { anomaliesState } from '../../assets/AtomStore/AtomStore';

export default function TimelineGraph() {
    const anomalies = useRecoilValue(anomaliesState);
    return (
        <>
            {
                anomalies &&
                (
                    <Chrono
                      items={anomalies}
                      mode="HORIZONTAL"
                      scrollable
                      theme={{
                            primary: '#E80057',
                            secondary: 'white',
                            cardBgColor: 'black',
                            cardForeColor: 'white',
                            titleColor: '#E80057',
                        }}
                    />
                )
            }
        </>
    );
}
