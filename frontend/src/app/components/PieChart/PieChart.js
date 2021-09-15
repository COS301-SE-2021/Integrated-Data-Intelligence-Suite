import React from 'react';
import {
    VictoryAxis, VictoryChart, VictoryContainer, VictoryLegend, VictoryPie, VictoryTheme,
} from 'victory';
import './PieChart.css';
import PieChartMock from '../../Mocks/PieChartDataMock.json';
import {DATA} from "../../Mocks/BarGraphMock";
import PieCustomLabel from "../PieCustomLabel/PieCustomLabel";
import PieDataMock from "../../Mocks/PieChartMock";

const index = 10;
let sum_of_slices = 0;
let data_from_backend;

export default class PieChart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            sumOfSlices: 0,
            data_points: null,
        };
        // TODO change data to [] or correct value from text array
        if (typeof props.text === 'undefined' || typeof props.text[index] === 'undefined' || props.text[index].length === 0) {
            this.setState({ data_points: PieDataMock() });
        } else if (props.text[index].length > 0) {
            this.setState({ data_points: props.text[index]});
        }
    }

    static getDerivedStateFromProps(props) {
        console.log("pie chart changing")
        if (props.text !== null && props.text !== '') {
            if (props.text[index] && props.text[index].length > 0) {
                // console.log("comparing data")
                // console.log(PieChartMock);
                // console.log(props.text[index]);
                return props.text[index];
            }
        }
        return { data_points: PieDataMock() };
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
                {/*<VictoryPie*/}
                {/*  colorScale={['#FF0000', '#FFFF00', '#00a800']}*/}
                {/*  data={this.state.data_points}*/}
                {/*  // labels={({ datum }) => `${datum.x} ${datum.y}%`}*/}
                {/*  innerRadius={200}*/}
                {/*  // style={{ labels: { fontSize: 30, padding: 25 } }}*/}
                {/*  labelComponent={<PieCustomLabel/>}*/}
                {/*  animate={{*/}
                {/*        duration: 500,*/}
                {/*    }}*/}
                {/*  */}
                {/*  */}
                {/*/>*/}
                <VictoryPie
                    style={{ labels: { fill: 'darkgrey', fontWeight: 600 } }}
                    colorScale={['#5873f9', '#FFFF00', '#00a800']}
                    innerRadius={100}
                    labelRadius={120}
                    labels={({ datum }) => [datum.x]}
                    labelComponent={<PieCustomLabel/>}
                    data={this.state.data_points}
                    // events={[{
                    //     target: 'data',
                    //     eventHandlers: {
                    //         onMouseOver: () => {
                    //             return [
                    //                 {
                    //                     target: 'data',
                    //                     mutation: (props) => {
                    //                         // this._changeSliceSum(style.fill === '#c43a31');
                    //                         // return style.fill === '#c43a31'
                    //                         //     ? null
                    //                         //     : { style: { fill: '#c43a31' } };
                    //                         return {
                    //                             style: { ...props.style, fill: '#5773FA' },
                    //                         };
                    //                     },
                    //                 },
                    //             ];
                    //         },
                    //         onMouseOut: () => {
                    //             return [
                    //                 {
                    //                     target: 'data',
                    //                     mutation: () =>{
                    //                         return null;
                    //                     },
                    //                 },
                    //             ];
                    //         },
                    //     },
                    // }]}
                />
            </>
        );
    }
}
