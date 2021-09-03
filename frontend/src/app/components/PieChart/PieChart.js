import React from 'react';
import { VictoryAxis, VictoryChart, VictoryContainer, VictoryLegend, VictoryPie } from 'victory';
import './PieChart.css';

function PieChart(props) {

    let data_from_backend;
    console.log(props.text);

    if (typeof props.text === 'undefined') {
        data_from_backend = [];
    } else if (typeof props.text[6] === 'undefined') {
        data_from_backend = [];
    } else if (props.text[6].length === 0) {
        data_from_backend = [];
    } else if (props.text[6].length > 0) {
        console.log('Reached-here-PPPPPPPP');
        // console.log(props.text[7][0].words);
        console.log();
        data_from_backend = props.text[6];
    }

    return (
        <>
            <div className="graph-container">
                <div
                    id={'total-sentiment-pie-container'}
                >
                    <VictoryPie
                        colorScale={['#FF0000', '#ff7707', '#FFFF00', '#138808', '#00E000']}
                        data={data_from_backend}
                        labels={() => null}
                        innerRadius={140}
                        padAngle={1.3}
                        height={500}
                        width={500}
                        animate={{
                            duration: 1000
                        }}
                        id={props.pieID}
                    />
                    <div id={props.legendID}>
                        <VictoryLegend
                            orientation={props.legendOrientation}
                            height={100}
                            // width={300}
                            gutter={20}
                            containerComponent={<VictoryContainer responsive/>}
                            data={[
                                {
                                    name: 'Very Bad',
                                    symbol: {
                                        fill: '#FF0000',
                                    }
                                },
                                {
                                    name: 'Bad',
                                    symbol: { fill: '#ff7707' }
                                },
                                {
                                    name: 'Neutral',
                                    symbol: { fill: '#FFFF00' }
                                },
                                {
                                    name: 'Good',
                                    symbol: { fill: '#138808' }
                                },
                                {
                                    name: 'Very Good',
                                    symbol: { fill: '#00E000' }
                                }
                            ]}
                        />
                    </div>

                </div>

            </div>
        </>
    );
}

export default PieChart;
