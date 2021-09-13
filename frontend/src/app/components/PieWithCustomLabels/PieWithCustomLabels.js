import React from 'react';
import { VictoryLabel, VictoryPie, VictoryTooltip } from 'victory';
import PieCustomLabel from '../PieCustomLabel/PieCustomLabel';

export default class PieWithCustomLabels extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <VictoryPie
                    style={{ labels: { fill: 'white' } }}
                    innerRadius={100}
                    labelRadius={120}
                    labels={({ datum }) => `# ${datum.y}`}
                    labelComponent={<PieCustomLabel/>}
                    data={this.props.text}
                />

            </>
        );
    }
}
