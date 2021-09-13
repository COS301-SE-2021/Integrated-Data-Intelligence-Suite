import React from 'react';
import {
 AnimatedBarSeries, AnimatedBarStack, XYChart, AnimatedAxis, Tooltip,
} from '@visx/xychart';
import cityTemperature, { CityTemperature } from '@visx/mock-data/lib/mocks/cityTemperature';
import * as Mock from '@visx/mock-data';

const data1 = [
    {
        x: 'jan',
        y: 50,
    },
    {
        x: 'feb',
        y: 10,
    },
    {
        x: 'mar',
        y: 20,
    },
    {
        x: 'apr',
        y: 14,
    },
    {
        x: 'may',
        y: 19,
    },
    {
        x: 'jun',
        y: 45,
    },
    {
        x: 'jul',
        y: 53,
    },
    {
        x: 'aug',
        y: 23,
    },
    {
        x: 'sept',
        y: 13,
    },
    {
        x: 'oct',
        y: 43,
    },
    {
        x: 'nov',
        y: 63,
    },
];
const data2 = [
    {
        x: 'jan',
        y: 40,
    },
    {
        x: 'feb',
        y: 5,
    },
    {
        x: 'mar',
        y: 10,
    },
    {
        x: 'apr',
        y: 11,
    },
    {
        x: 'may',
        y: 29,
    },
    {
        x: 'jun',
        y: 35,
    },
    {
        x: 'jul',
        y: 43,
    },
    {
        x: 'aug',
        y: 13,
    },
    {
        x: 'sept',
        y: 53,
    },
    {
        x: 'oct',
        y: 63,
    },
    {
        x: 'nov',
        y: 33,
    },
];

const accessors = {
    xAccessor: (d) => d.x,
    yAccessor: (d) => d.y,
};

const points1 = Mock.genRandomNormalPoints();
const points2 = Mock.genRandomNormalPoints();

export default function VisxBarStackGraph(props) {
    // noinspection RequiredAttributes
    return (
        <XYChart
          xScale={{ type: 'band' }}
          yScale={{ type: 'linear' }}
          height={250}
          width={250}
        >
            <AnimatedAxis
              orientation="bottom"
              hideTicks
              numTicks={4}
              stroke="black"
                // strokeWidth={'1em'}
                // top={40}
            />

            <AnimatedAxis
              orientation="left"
              hideTicks
              numTicks={4}
            />
            <AnimatedBarStack>
                <AnimatedBarSeries
                  dataKey="New York"
                  data={data1}
                  {...accessors}
                />
                <AnimatedBarSeries
                  dataKey="San Francisco"
                  data={data2}
                  {...accessors}
                />
            </AnimatedBarStack>

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
                        {accessors.xAccessor(tooltipData.nearestDatum.datum)}
                        {', '}
                        {accessors.yAccessor(tooltipData.nearestDatum.datum)}
                    </div>
                )}
            />
        </XYChart>
    );
}
