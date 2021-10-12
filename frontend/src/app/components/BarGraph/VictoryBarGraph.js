import React from 'react';
import {
 VictoryAxis, VictoryBar, VictoryChart, VictoryLabel, VictoryTooltip,
} from 'victory';

// Color States
const ColorDefault = '#4666FF';
const ColorGray = '#CAD0E3';

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
                    },
                }}
              fixLabelOverlap
              axisLabelComponent={<VictoryLabel dx={-12} />}
            />

            <VictoryAxis
              dependentAxis
              label={yAxisLabel}
              fixLabelOverlap
            />
            <VictoryBar
              cornerRadius={15}
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
                    flyoutHeight={40}
                    flyoutWidth={40}
                    flyoutPadding={10}
                    flyoutStyle={{
                            stroke: '#4666FF',
                            strokeWidth: 2,
                            fill: 'white',
                        }}
                    style={{ fill: '#4666FF' }}
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
