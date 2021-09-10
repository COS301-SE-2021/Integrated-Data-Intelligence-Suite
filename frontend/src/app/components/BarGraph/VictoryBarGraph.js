import React from 'react';
import { VictoryChart, VictoryBar, VictoryTooltip } from 'victory';

// Color States
const COLOR_DEFAULT = 'red';
const COLOR_NOTHOVER = 'gray';

// Test Data
const SampleData = [
    {
        x: 1,
        y: 2
    },
    {
        x: 2,
        y: 4
    },
    {
        x: 3,
        y: 1
    },
    {
        x: 4,
        y: 5
    }
];

export default function VictoryBarGraph(props) {

    return (
        <VictoryChart>
            <VictoryBar
                domain={{
                    x: [0, 5],
                    y: [0, 5]
                }}
                name="bars"
                style={{ data: { fill: COLOR_DEFAULT } }}
                labels={({ datum }) => [datum.y, datum.x]}
                labelComponent={(
                    <VictoryTooltip
                        constrainToVisibleArea
                        flyoutHeight={20}
                        flyoutWidth={20}
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
                                                fill: COLOR_NOTHOVER
                                            }
                                        })
                                    },
                                    {
                                        target: 'data',
                                        mutation: () => ({
                                            style: {
                                                fill: COLOR_DEFAULT
                                            }
                                        })
                                    }
                                ];
                            },
                            onMouseLeave: () => {
                                return [
                                    {
                                        eventKey: 'all',
                                        target: 'data',
                                        mutation: () => ({
                                            style: {
                                                fill: COLOR_DEFAULT
                                            }
                                        })
                                    },
                                    {
                                        target: 'data',
                                        mutation: () => ({
                                            style: {
                                                fill: COLOR_DEFAULT
                                            }
                                        })
                                    }
                                ];
                            }
                        }
                    }
                ]}
                data={SampleData}
            />
        </VictoryChart>
    );
}

