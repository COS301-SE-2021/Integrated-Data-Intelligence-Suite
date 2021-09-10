import React from 'react';
import { VictoryLabel, VictoryPie, VictoryTooltip } from 'victory';

export default class PieCustomLabel extends React.Component {
    render() {
        return (
            <g>
                <VictoryLabel {...this.props}/>
                <VictoryTooltip
                    {...this.props}
                    x={200}
                    y={250}
                    orientation="top"
                    pointerLength={0}
                    cornerRadius={50}
                    flyoutWidth={100}
                    flyoutHeight={100}
                    flyoutStyle={{ fill: 'black' }}
                />
            </g>
        );
    }
}
PieCustomLabel.defaultEvents = VictoryTooltip.defaultEvents;
