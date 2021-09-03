import React from 'react';
import { VictoryAxis, VictoryChart, VictoryLegend, VictoryPie } from 'victory';
import './PieChart.css';

function PieChart(props) {
    return (
        <>
            <div className="graph-container">
                <div
                    id={'total-sentiment-pie-container'}
                >
                    <VictoryPie
                        colorScale={['#FF0000', '#ff7707', '#FFFF00', '#138808', '#00E000']}
                        data={props.graphData}
                        labels={() => null}
                        innerRadius={140}
                        padAngle={1.1}
                        height={500}
                        width={500}
                        animate={{
                            duration: 1000
                        }}
                        id={props.pieID}
                    />
                    <VictoryLegend
                        x={125}
                        y={50}
                        orientation={props.legendOrientation}
                        gutter={20}
                        style={{
                            border: { stroke: 'black' },
                            title: { fontSize: 20 }
                        }}
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
        </>
    );
}

export default PieChart;
