import React from 'react';
import {
    AreaSeries,
    LineSeries,
    XYPlot,
    FlexibleXYPlot,
    FlexibleWidthXYPlot,
    FlexibleHeightXYPlot,
} from 'react-vis';

export default function AreaGraph(props) {
    const graphData = [
        {
            x: 0,
            y: 10,
        },
        {
            x: 1,
            y: 9.624510357540071,
        },
        {
            x: 2,
            y: 9.678457084785416,
        },
        {
            x: 3,
            y: 9.920123014737754,
        },
        {
            x: 4,
            y: 10.034421777972968,
        },
        {
            x: 5,
            y: 10.220262517122697,
        },
        {
            x: 6,
            y: 9.939524547148082,
        },
        {
            x: 7,
            y: 9.965580085826183,
        },
        {
            x: 8,
            y: 10.319500202390525,
        },
        {
            x: 9,
            y: 10.80200667241078,
        },
        {
            x: 10,
            y: 10.509352861193785,
        },
        {
            x: 11,
            y: 11.07054414030429,
        },
        {
            x: 12,
            y: 11.080285370408212,
        },
        {
            x: 13,
            y: 11.607176972599856,
        },
        {
            x: 14,
            y: 11.749354978627412,
        },
        {
            x: 15,
            y: 11.167955395260195,
        },
        {
            x: 16,
            y: 11.088355202874588,
        },
        {
            x: 17,
            y: 11.032947868282507,
        },
        {
            x: 18,
            y: 10.102274302993141,
        },
        {
            x: 19,
            y: 9.583084078329179,
        },
        {
            x: 20,
            y: 9.445384155652846,
        },
    ];

    return (
        <>
            <FlexibleXYPlot>
                <AreaSeries
                  data={graphData}
                  opacity={0.25}
                  stroke="transparent"
                  style={{}}
                />
                <LineSeries
                  data={graphData}
                  opacity={1}
                  stroke="red"
                  strokeStyle="solid"
                  style={{}}
                />
            </FlexibleXYPlot>
        </>
    );
}
