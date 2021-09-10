import React, { SVGProps } from 'react';
import {
    AnimatedAxis, // any of these can be non-animated equivalents
    AnimatedGrid,
    AnimatedLineSeries,
    XYChart,
    Tooltip,
} from '@visx/xychart';

function VisxLineGraph(props) {
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

    const data3 = [
        {
            x: 'jan \'21',
            y: 30,
        },
        {
            x: 'feb \'21',
            y: 31,
        },
        {
            x: 'mar \'21',
            y: 40,
        },
        {
            x: 'apr \'21',
            y: 80,
        },
    ];

    const data4 = [
        {
            x: 'jan \'21',
            y: 30,
        },
        {
            x: 'feb \'21',
            y: 31,
        },
        {
            x: 'mar \'21',
            y: 40,
        },
        {
            x: 'apr \'21',
            y: 80,
        },
    ];

    const data5 = [
        {
            x: 'jan \'21',
            y: 30,
        },
        {
            x: 'feb \'21',
            y: 31,
        },
        {
            x: 'mar \'21',
            y: 40,
        },
        {
            x: 'apr \'21',
            y: 80,
        },
    ];

    const accessors = {
        xAccessor: (d) => d.x,
        yAccessor: (d) => d.y,
    };

    return (
        <>
            <XYChart
              height={200}
              width={250}
              xScale={{ type: 'band' }}
              yScale={{ type: 'linear' }}
            >
                <AnimatedAxis
                  orientation="bottom"
                  hideTicks
                  numTicks={4}
                  stroke="black"
                    // strokeWidth={'1em'}
                />
                <AnimatedGrid
                  columns={false}
                  rows={false}
                  numTicks={4}
                  animationTrajectory="max"
                />
                <AnimatedLineSeries
                  dataKey="Line1"
                  data={data1}
                  {...accessors}

                />
                <AnimatedLineSeries
                  dataKey="Line2"
                  data={data2}
                  {...accessors}
                />
                <Tooltip
                  snapTooltipToDatumX
                  snapTooltipToDatumY
                  showVerticalCrosshair
                  showSeriesGlyphs
                  renderTooltip={({
                        tooltipData,
                        colorScale,
                    }) => (
                        // The k
                        <div>
                            {/* First Line */}
                            <div
                              style={{
                                    color: colorScale(Object.keys(tooltipData.datumByKey)[0]),
                                }}
                            >

                                {Object.keys(tooltipData.datumByKey)[0]}
                                {': '}
                                {
                                    tooltipData.datumByKey.Line1 === null || tooltipData.datumByKey.Line1 === undefined
                                        ? '–'
                                        : tooltipData.datumByKey.Line1.datum.y
                                }
                            </div>

                            {/* Second Line */}
                            <div
                              style={{
                                    color: colorScale(Object.keys(tooltipData.datumByKey)[1]),
                                }}
                            >
                                <br />
                                {Object.keys(tooltipData.datumByKey)[1]}
                                {': '}
                                {
                                    tooltipData.datumByKey.Line2 === null || tooltipData.datumByKey.Line2 === undefined
                                        ? '–'
                                        : tooltipData.datumByKey.Line2.datum.y
                                }
                            </div>
                        </div>
                    )}
                />
            </XYChart>
        </>
    );
}

export default VisxLineGraph;
