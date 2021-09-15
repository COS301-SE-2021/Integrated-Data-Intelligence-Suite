import React from 'react';
import {
    VictoryAxis, VictoryChart, VictoryContainer, VictoryLegend, VictoryPie, VictoryTheme,
} from 'victory';
import './PieChart.css';
import PieChartMock from '../../Mocks/PieChartDataMock.json';
import { DATA } from '../../Mocks/BarGraphMock';
import PieCustomLabel from '../PieCustomLabel/PieCustomLabel';
import PieDataMock from '../../Mocks/PieChartMock';

const index = 10;
const colors = [
    '#25bd52',
    '#786e64',
    '#8e8073',
    '#e66c85',
    '#244a99',
    '#be8abf',
    '#b3b456',
    '#d79525',
    '#bd6341',
    '#709a04',
    '#5fa7c8',
    '#f3cd6c',
    '#FF0800',
    '#5fa7c8',
];

let sum_of_slices =0;

export default class PieChart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            sumOfSlices: 0,
            data_points: PieDataMock(),
        };
        // TODO change data to [] or correct value from text array
        if (typeof props.text === 'undefined' || typeof props.text[index] === 'undefined' || props.text[index].length === 0) {
            this.setState({ data_points: PieDataMock() });
        } else if (props.text[index].length > 0) {
            this.setState({ data_points: props.text[index]});
        }
    }

    static getDerivedStateFromProps(props) {
        console.log('pie chart changing');
        if (props.text !== null && props.text !== '') {
            if (props.text[index] && props.text[index].length > 0) {
                // console.log("comparing data")
                // console.log(PieChartMock);
                // console.log(props.text[index]);
                return { data_points: props.text[index] }
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
                {/* <VictoryPie */}
                {/*  colorScale={['#FF0000', '#FFFF00', '#00a800']} */}
                {/*  data={this.state.data_points} */}
                {/*  // labels={({ datum }) => `${datum.x} ${datum.y}%`} */}
                {/*  innerRadius={200} */}
                {/*  // style={{ labels: { fontSize: 30, padding: 25 } }} */}
                {/*  labelComponent={<PieCustomLabel/>} */}
                {/*  animate={{ */}
                {/*        duration: 500, */}
                {/*    }} */}
                {/*  */}
                {/*  */}
                {/* /> */}
                {this.state.data_points && (
                    <VictoryPie
                      style={{ labels: { fill: 'darkgrey', fontWeight: 600 } }}
                      colorScale={colors}
                      innerRadius={100}
                      labelRadius={120}
                      labels={({ datum }) => [datum.x, datum.y]}
                      labelComponent={<PieCustomLabel />}
                      data={this.state.data_points}
                    />
)}
            </>
        );
    }
}
