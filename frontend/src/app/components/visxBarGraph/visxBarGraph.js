import React from 'react';
import { AnimatedBarSeries, AnimatedBarStack, XYChart, AnimatedAxis, Tooltip } from '@visx/xychart';
import cityTemperature, { CityTemperature } from '@visx/mock-data/lib/mocks/cityTemperature';
import * as Mock from '@visx/mock-data';

export type XYChartProps = {
    width: number;
    height: number;
};

const data1 = [
    {
        x: 'jan \'21',
        y: 50,
    },
    {
        x: 'feb \'21',
        y: 10,
    },
    {
        x: 'mar \'21',
        y: 20,
    },
    {
        x: 'apr \'21',
        y: 14,
    },
    {
        x: 'may \'21',
        y: 19,
    },
    {
        x: 'jun \'21',
        y: 45,
    },
    {
        x: 'jul \'21',
        y: 53,
    },
    {
        x: 'aug \'21',
        y: 23,
    },
    {
        x: 'sept \'21',
        y: 13,
    },
    {
        x: 'oct \'21',
        y: 43,
    },
    {
        x: 'nov \'21',
        y: 63,
    },
];
const data2 = [
    {
        x: 'jan \'21',
        y: 40,
    },
    {
        x: 'feb \'21',
        y: 5,
    },
    {
        x: 'mar \'21',
        y: 10,
    },
    {
        x: 'apr \'21',
        y: 11,
    },
    {
        x: 'may \'21',
        y: 29,
    },
    {
        x: 'jun \'21',
        y: 35,
    },
    {
        x: 'jul \'21',
        y: 43,
    },
    {
        x: 'aug \'21',
        y: 13,
    },
    {
        x: 'sept \'21',
        y: 53,
    },
    {
        x: 'oct \'21',
        y: 63,
    },
    {
        x: 'nov \'21',
        y: 33,
    },
];

const accessors = {
    xAccessor: (d) => d.x,
    yAccessor: (d) => d.y,
};

const points1 = Mock.genRandomNormalPoints();
const points2 = Mock.genRandomNormalPoints();

export default function VisxBarGraph(props) {
    // noinspection RequiredAttributes
    return (
        <XYChart
            xScale={{ type: 'band' }}
            yScale={{ type: 'linear' }}
            height={300}
            width={300}
        >
            <AnimatedAxis
                orientation="top"
                hideTicks
                numTicks={4}
                stroke={'black'}
                // strokeWidth={'1em'}
                top={40}
            />

            <AnimatedAxis orientation={'left'}/>
            <AnimatedBarSeries
                dataKey="Line1"
                data={data1}
                {...accessors}
            />

            <Tooltip
                snapTooltipToDatumX
                snapTooltipToDatumY
                showVerticalCrosshair
                renderTooltip={({
                    tooltipData,
                    colorScale,
                }) => (
                    <div>
                        <div style={{ color: colorScale(tooltipData.nearestDatum.key) }}>
                            {tooltipData.nearestDatum.key}
                        </div>
                        {accessors.yAccessor(tooltipData.nearestDatum.datum)}
                    </div>
                )}
            />
        </XYChart>
    );
}
