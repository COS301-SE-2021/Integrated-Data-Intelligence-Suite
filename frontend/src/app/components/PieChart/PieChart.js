import React from 'react';
import { VictoryPie } from 'victory';
import './PieChart.css';
import PieCustomLabel from '../PieCustomLabel/PieCustomLabel';

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


const PieChart = ({dominantWords}) => {
    return (
        <>
            {
                dominantWords &&
                (
                    <VictoryPie
                      style={{ labels: { fill: 'darkgrey', fontWeight: 600 } }}
                      colorScale={colors}
                      innerRadius={100}
                      labelRadius={120}
                      labels={({ datum }) => [datum.x, `${datum.y}%`]}
                      labelComponent={<PieCustomLabel />}
                      data={dominantWords}
                    />
                )
            }
        </>
    );
};

export default PieChart;
