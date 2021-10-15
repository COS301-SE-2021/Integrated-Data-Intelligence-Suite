import React, { SVGProps } from 'react';
import {
    AnimatedAxis, // any of these can be non-animated equivalents
    AnimatedGrid,
    AnimatedLineSeries,
    XYChart,
    Tooltip, AnimatedAreaSeries,
} from '@visx/xychart';
import { curveLinear, curveStep, curveCardinal } from '@visx/curve';
import './visxAreaGraph.css';

export default function VisxAreaGraph(props) {
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

    let data_from_backend;
    // console.log(props.text);

    if (typeof props.text === 'undefined') {
        data_from_backend = [];
    } else if (typeof props.text[3] === 'undefined') {
        data_from_backend = [];
    } else if (props.text[3].length === 0) {
        data_from_backend = [];
    } else if (props.text[3].length > 0) {
        // console.log('Reached-here-PPPPPPPP');
        // console.log(props.text[7][0].words);
        data_from_backend = props.text[3];
    }

    return (
        <>
            <XYChart
              height={200}
              width={250}
              xScale={{ type: 'band' }}
              yScale={{ type: 'linear' }}
            >
                {
                    props.showYAxis
                    && (
                        <AnimatedAxis
                          orientation="bottom"
                          hideTicks
                            // numTicks={4}
                          stroke="black"
                          hideZero
                        />
                    )
                }
                {
                    props.showXAxis
                    && (
                        <AnimatedAxis
                          orientation="left"
                          hideTicks
                          numTicks={4}
                          stroke="black"
                          hideZero
                        />
                    )
                }

                <AnimatedGrid
                  columns={false}
                  rows={false}
                  numTicks={4}
                  animationTrajectory="max"
                />

                <AnimatedAreaSeries
                  dataKey="Line1"
                  data={data2}
                  key={props.text}
                  {...accessors}
                  fillOpacity={0.4}
                  curve={curveCardinal}
                  className={props.idName}

                />
                {
                    props.showSecondLine
                    && (
                        <AnimatedAreaSeries
                          dataKey=" Line2"
                          data={data2}
                          {...accessors}
                          fillOpacity={0.4}
                          curve={curveCardinal}
                        />
                    )
                }

                <Tooltip
                  snapTooltipToDatumX
                  showVerticalCrosshair
                  showDatumGlyph
                  glyphStyle={({
                        r: 20,
                        glyphName: 'me-myron',
                        className: 'glyph-class',
                        id: 'this-glyph',
                    })}
                  renderTooltip={({
                        tooltipData,
                        colorScale,
                    }) => (
                        <div>
                            {/* First Line */}
                            <div
                              style={{
                                    color: props.tooltipKeyColor,
                                }}
                            >
                                {Object.keys(tooltipData.datumByKey)[0]}
                                {': '}
                            </div>
                            {
                                tooltipData.datumByKey.Line1 === null || tooltipData.datumByKey.Line1 === undefined
                                    ? '–'
                                    : tooltipData.datumByKey.Line1.datum.y
                            }

                            {/* Second Line */}
                            {
                                props.showSecondLine
                                && (

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
                                )
                            }
                        </div>
                    )}
                />
            </XYChart>
        </>
    );
}
