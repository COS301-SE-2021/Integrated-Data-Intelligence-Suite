import React from 'react';
import { VictoryPie } from 'victory';
import PieCustomLabel from '../PieCustomLabel/PieCustomLabel';

export default function PieWithCustomLabels({ text }) {
    return (
        <>
            <VictoryPie
              animate={{ duration: 1000 }}
              style={{ labels: { fill: 'white' } }}
              innerRadius={130}
              labelComponent={<PieCustomLabel />}
              data={text}
              colorScale={['#208AAE', '#E80057']}
            />
        </>
    );
}
