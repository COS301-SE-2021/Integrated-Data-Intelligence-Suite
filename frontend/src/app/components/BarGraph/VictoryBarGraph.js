import React from 'react';
import { VictoryChart, VictoryBar, VictoryTooltip } from 'victory';

// Color States
const COLOR_DEFAULT = '#4666FF';
const COLOR_NOTHOVER = '#CAD0E3';

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

    console.log("inside victory bar graph");
    // console.log(this.props);

    return (
        <VictoryChart>
            <VictoryBar
                animate={{
                    duration: 1000,
                    onLoad: { duration: 500 }
                }}
                cornerRadius={15}
                alignment="middle"
                padding={{
                    top: 20,
                    bottom: 60
                }}
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
                        flyoutHeight={40}
                        flyoutWidth={40}
                        flyoutPadding={10}
                        flyoutStyle={{
                            stroke: "#4666FF",
                            strokeWidth: 2,
                            fill: "white",
                        }}
                        style={{ fill: "#4666FF" }}
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
                data={this.props.text}
            />
        </VictoryChart>
    );
}

