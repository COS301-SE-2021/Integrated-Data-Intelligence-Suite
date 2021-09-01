import React, { useEffect, useRef, useState } from 'react';
import { VictoryAxis, VictoryBar, VictoryChart, VictoryLine, VictoryTheme } from 'victory';
import LineGraphDataPositive from '../../Mocks/LineGraphDataPositive.json';

function BarGraph(props) {
    return (
        <>
            <div className={'graph-container'}>
                <VictoryChart>
                    <VictoryBar
                        horizontal
                        alignment="middle"
                        style={{
                            data: { fill: '#c43a31' }
                        }}
                        data={props.graphData}
                        labels={['10%', '15%', '40%', '20%', '45%']}
                    />

                    <VictoryAxis
                        standalone={false}
                        tickValues={['Canada', 'USA', 'Germany', 'SA', 'Brazil']}
                        style={{
                            ticks: { stroke: 'transparent' },
                            // tickLabels: { fill: 'transparent' }
                        }}
                    />
                </VictoryChart>
            </div>
        </>
    );
}

export default BarGraph;

