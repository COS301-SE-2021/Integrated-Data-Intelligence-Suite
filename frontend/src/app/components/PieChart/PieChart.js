import React from 'react';
import {
    VictoryAxis, VictoryChart, VictoryContainer, VictoryLegend, VictoryPie,
} from 'victory';
import './PieChart.css';
import PieChartMock from '../../Mocks/PieChartDataMock.json';

let sum_of_slices = 0;
let data_from_backend;

export default class PieChart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            sumOfSlices: 0
        };
    }

    _changeSliceSum(value) {
        if (value) {
            sum_of_slices -= 1;
        } else {
            sum_of_slices += 1;
        }
        this.setState({ sumOfSlices: sum_of_slices });

        console.log(sum_of_slices);
    }

    // console;
// .
//
//     log(props
//
// .
//     text;
// )
//     ;
//
//     if(
//
//     typeof;
//     props;
// .
//     text;
// ===
//     'undefined';
// ) {
//     data_from_backend = [];
// }
// else
// if (typeof props.text[6] === 'undefined') {
//     data_from_backend = [];
// } else if (props.text[6].length === 0) {
//     data_from_backend = [];
// } else if (props.text[6].length > 0) {
//     // console.log('Reached-here-PPPPPPPP');
//     // console.log(props.text[7][0].words);
//     console.log();
//     data_from_backend = props.text[6];
// }
    render() {

        return (
            <>
                <div className="graph-container">
                    <div
                        id="total-sentiment-pie-container"
                    >
                        <VictoryPie
                            colorScale={['#FF0000', '#ff7707', '#FFFF00', '#138808', '#00E000']}
                            data={PieChartMock}
                            labels={() => null}
                            // innerRadius={140}
                            padAngle={1.5}
                            height={700}
                            width={700}
                            animate={{
                                duration: 500,
                            }}
                            id={this.props.pieID}
                            events={[{
                                target: 'data',
                                eventHandlers: {
                                    onClick: () => {
                                        return [
                                            {
                                                target: 'data',
                                                mutation: ({ style }) => {
                                                    this._changeSliceSum(style.fill === '#c43a31');
                                                    return style.fill === '#c43a31'
                                                        ? null
                                                        : { style: { fill: '#c43a31' } };
                                                }
                                            }
                                        ];
                                    }
                                }
                            }]}
                        />
                        <div id={this.props.legendID}>
                            <VictoryLegend
                                orientation={this.props.legendOrientation}
                                height={100}
                                // width={300}
                                gutter={20}
                                containerComponent={<VictoryContainer responsive/>}
                                data={[
                                    {
                                        name: 'Very Bad',
                                        symbol: {
                                            fill: '#FF0000',
                                        },
                                    },
                                    {
                                        name: 'Bad',
                                        symbol: { fill: '#ff7707' },
                                    },
                                    {
                                        name: 'Neutral',
                                        symbol: { fill: '#FFFF00' },
                                    },
                                    {
                                        name: 'Good',
                                        symbol: { fill: '#138808' },
                                    },
                                    {
                                        name: 'Very Good',
                                        symbol: { fill: '#00E000' },
                                    },
                                ]}
                            />
                        </div>

                    </div>
                    <div>{this.state.sumOfSlices}</div>
                </div>
            </>
        );
    }

}
