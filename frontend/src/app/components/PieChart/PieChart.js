import React from 'react';
import {
    VictoryAxis, VictoryChart, VictoryContainer, VictoryLegend, VictoryPie,
} from 'victory';
import './PieChart.css';
import PieChartMock from '../../Mocks/PieChartDataMock.json';
import { DATA } from '../../Mocks/BarGraphMock';

let sum_of_slices = 0;
let data_from_backend;

export default class PieChart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            sumOfSlices: 0,
            data_points: PieChartMock,
        };
        // TODO change data to [] or correct value from text array
        if (typeof props.text === 'undefined' || typeof props.text[0] === 'undefined' || props.text[0].length === 0) {
            this.setState({ data_points: PieChartMock });
        } else if (props.text[0].length > 0) {
            this.setState({ data_points: PieChartMock });
        }
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
                <div className="pie-container">
                    <div className="sum-of-slices">{this.state.sumOfSlices}</div>
                    <div
                      id="total-sentiment-pie-container"
                    >
                        <VictoryPie
                          colorScale={['#FF0000', '#FFFF00', '#00a800']}
                          data={this.state.data_points}
                          labels={({ datum }) => datum.x}
                          innerRadius={100}
                          padAngle={5.5}
                          width={400}
                          style={{ labels: { fontSize: 45, padding: 20 } }}
                          animate={{
                                duration: 500,
                            }}
                          id={this.props.pieID}
                          events={[{
                                target: 'data',
                                eventHandlers: {
                                    onMouseOver: () => {
                                        return [
                                            {
                                                target: 'data',
                                                mutation: (props) => {
                                                    // this._changeSliceSum(props.style.fill === '#c43a31');
                                                    // return style.fill === '#c43a31'
                                                    //     ? null
                                                    //     : { style: { fill: '#c43a31' } };
                                                    return {
                                                        style: { ...props.style, fill: '#5773FA' },
                                                    };
                                                },
                                            },
                                        ];
                                    },
                                    onMouseOut: () => {
                                        return [
                                            {
                                                target: 'data',
                                                mutation: () =>{
                                                    return null;
                                                }
                                            }
                                        ]
                                    }
                                },
                            }]}
                        />
                        {/* <div id={this.props.legendID}> */}
                        {/*    <VictoryLegend */}
                        {/*        orientation={this.props.legendOrientation} */}
                        {/*        height={100} */}
                        {/*        // width={300} */}
                        {/*        gutter={20} */}
                        {/*        containerComponent={<VictoryContainer responsive/>} */}
                        {/*        data={[ */}
                        {/*            { */}
                        {/*                name: 'Very Bad', */}
                        {/*                symbol: { */}
                        {/*                    fill: '#FF0000', */}
                        {/*                }, */}
                        {/*            }, */}
                        {/*            { */}
                        {/*                name: 'Bad', */}
                        {/*                symbol: { fill: '#ff7707' }, */}
                        {/*            }, */}
                        {/*            { */}
                        {/*                name: 'Neutral', */}
                        {/*                symbol: { fill: '#FFFF00' }, */}
                        {/*            }, */}
                        {/*            { */}
                        {/*                name: 'Good', */}
                        {/*                symbol: { fill: '#138808' }, */}
                        {/*            }, */}
                        {/*            { */}
                        {/*                name: 'Very Good', */}
                        {/*                symbol: { fill: '#00E000' }, */}
                        {/*            }, */}
                        {/*        ]} */}
                        {/*    /> */}
                        {/* </div> */}
                    </div>
                </div>
            </>
        );
    }
}
