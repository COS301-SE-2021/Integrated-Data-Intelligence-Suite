import React from 'react';
import {
    VictoryAxis,
    VictoryBar,
    VictoryChart,
    VictoryLabel,
    VictoryTooltip,
} from 'victory';

// Color States
const ColorDefault = '#E80057';
const ColorGray = 'rgba(232,0,87,0.26)';

// eslint-disable-next-line react/prop-types
export default function VictoryBarGraph({ xAxisLabel, yAxisLabel, text }) {
    return (
        <VictoryChart
          domainPadding={25}
          animate={{ duration: 1000 }}
        >
            <VictoryAxis
              label={xAxisLabel}
              style={{
                    tickLabels: {
                        angle: 45,
                        textAnchor: 'start',
                        stroke: '#fff',
                    },
                    axis: { stroke: 'rgba(0,0,0,0)' },
                }}
              fixLabelOverlap
              axisLabelComponent={<VictoryLabel dx={-12} />}
            />
            <VictoryAxis
              dependentAxis
              label={yAxisLabel}
              fixLabelOverlap
              style={{
                    axis: { stroke: 'rgba(0,0,0,0)' },
                    grid: {
                        stroke: '#fff',
                        strokeDasharray: '4,8',
                        strokeWidth: 0.2,
                    },
                    tickLabels: {
                        stroke: '#fff',
                    },
                }}
            />
            <VictoryBar
              alignment="middle"
              padding={{
                    top: 20,
                    bottom: 60,
                }}
              name="bars"
              style={{ data: { fill: ColorDefault } }}
              labels={({ datum }) => [datum.y, datum.x]}
              labelComponent={(
                  <VictoryTooltip
                    constrainToVisibleArea
                    flyoutPadding={10}
                    flyoutStyle={{
                            stroke: ColorDefault,
                            strokeWidth: 2,
                            fill: ColorDefault,
                            marginBottom: 50,
                        }}
                    style={{ fill: '#fff' }}
                  />
                )}
              events={[
                    {
                        eventHandlers: {
                            onMouseEnter: () => {
                                return [
                                    {
                                        eventKey: 'all',
                                        target: 'data',
                                        mutation: () => ({
                                            style: {
                                                fill: ColorGray,
                                            },
                                        }),
                                    },
                                    {
                                        target: 'data',
                                        mutation: () => ({
                                            style: {
                                                fill: ColorDefault,
                                            },
                                        }),
                                    },
                                ];
                            },
                            onMouseLeave: () => {
                                return [
                                    {
                                        eventKey: 'all',
                                        target: 'data',
                                        mutation: () => ({
                                            style: {
                                                fill: ColorDefault,
                                            },
                                        }),
                                    },
                                    {
                                        target: 'data',
                                        mutation: () => ({
                                            style: {
                                                fill: ColorDefault,
                                            },
                                        }),
                                    },
                                ];
                            },
                        },
                    },
                ]}
              data={text}
                // samples={10}
            />
        </VictoryChart>
    );
}
