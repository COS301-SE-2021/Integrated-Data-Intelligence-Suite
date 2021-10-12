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
                    />
                )
            }
        </>
    );
}
