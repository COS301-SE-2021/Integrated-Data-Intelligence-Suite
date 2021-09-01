import React from 'react';
import { VictoryAxis, VictoryChart, VictoryPie } from 'victory';

function PieChart(props) {
    return (
        <>
            <div className={'graph-container'}>
                <VictoryPie
                    colorScale={['#FF0000', '#ff7707', '#FFFF00', '#138808', '#00E000']}
                    data={props.graphData}
                    innerRadius={100}
                    padAngle={2.8}
                    height={400}
                    width={500}
                />
            </div>
        </>
    );
}

export default PieChart;

