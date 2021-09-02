import React, { SVGProps } from 'react';
import {
    AnimatedAxis, // any of these can be non-animated equivalents
    AnimatedGrid,
    AnimatedLineSeries,
    XYChart,
    Tooltip, AnimatedAreaSeries, AnimatedAreaStack,
} from '@visx/xychart';
import { curveLinear, curveStep, curveCardinal } from '@visx/curve';

export default function VisxAreaStackGraph(props) {
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
    ];

    const data2 = [
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
            <XYChart height={300} width={400} xScale={{ type: 'band' }} yScale={{ type: 'linear' }}>
                <AnimatedAxis
                    orientation="bottom"
                    hideTicks
                    numTicks={4}
                    stroke={'black'}
                />
                <AnimatedGrid
                    columns={false}
                    rows={false}
                    // numTicks={4}
                    animationTrajectory={'max'}
                />

                <AnimatedAreaStack
                    curve={curveCardinal}

                >

                    <AnimatedAreaSeries
                        dataKey="Line1"
                        data={data1}
                        {...accessors}
                        fillOpacity={0.4}
                        // curve={curveCardinal}
                    />
                    <AnimatedAreaSeries
                        dataKey="Line2"
                        data={data2}
                        {...accessors}
                        fillOpacity={0.4}
                        // curve={curveCardinal}
                    />
                </AnimatedAreaStack>


                <Tooltip
                    snapTooltipToDatumX
                    snapTooltipToDatumY
                    showVerticalCrosshair
                    // showSeriesGlyphs
                    renderTooltip={({
                        tooltipData,
                        colorScale,
                    }) => (
                        //The k
                        <div>
                            {/*First Line*/}
                            <div
                                style={{
                                    color: colorScale(Object.keys(tooltipData.datumByKey)[0])
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

                            {/*Second Line*/}
                            <div
                                style={{
                                    color: colorScale(Object.keys(tooltipData.datumByKey)[1])
                                }}
                            >
                                <br/>
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

