import React from 'react';
import { VictoryChart, VictoryBar, VictoryTooltip, VictoryAxis, VictoryLabel } from 'victory';

// Color States
const COLOR_DEFAULT = '#4666FF';
const COLOR_NOTHOVER = '#CAD0E3';

// Test Data
let SampleData = [
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
let data_from_backend = null;
export default class VictoryBarGraph extends React.Component {
    constructor(props) {
        super(props);
    }

    _verifyDataFromBackend = function (backend_data) {
        console.log('inside_bar_graph');
        console.log(backend_data);
        if (backend_data !== 'undefined') {
            return backend_data;
        }
        return null;
    };

    render() {
        return (
            <VictoryChart
                domainPadding={25}
                animate={{ duration: 1000 }}
            >
                <VictoryAxis
                    label={this.props.xAxisLabel}
                    style={{
                        tickLabels: {
                            angle: 45,
                            textAnchor: 'start'
                        }
                    }}
                    fixLabelOverlap
                    axisLabelComponent={<VictoryLabel dx={-12}/>}
                />

                <VictoryAxis
                    dependentAxis
                    label={this.props.yAxisLabel}
                    fixLabelOverlap
                />
                <VictoryBar
                    cornerRadius={15}
                    alignment="middle"
                    padding={{
                        top: 20,
                        bottom: 60
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
                    data={this._verifyDataFromBackend(this.props.text)}
                    // samples={10}
                />
            </VictoryChart>
        );
    }
}

