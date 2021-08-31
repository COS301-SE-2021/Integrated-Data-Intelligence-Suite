import React, { useEffect, useRef, useState } from 'react';
import { VictoryAxis, VictoryChart, VictoryLine, VictoryTheme } from 'victory';
import LineGraphDataPositive from '../../Mocks/LineGraphDataPositive.json';

function IndicatorLineGraph(props) {
    return (
        <>
            <div className={'graph-container'}>
                <VictoryChart>
                    <VictoryLine
                        interpolation="natural"
                        style={{
                            data: { stroke: (props.lineColor) },
                            parent: { border: '1px solid #ccc' }
                        }}
                        data={props.graphData}
                    />

                    <VictoryAxis
                        standalone={false}
                        tickValues={['May', 'June', 'July', 'Aug', 'Sept']}
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

export default IndicatorLineGraph;

