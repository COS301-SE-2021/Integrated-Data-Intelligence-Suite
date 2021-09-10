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
                    data={[
                        {
                            x: 1,
                            y: 5
                        },
                        {
                            x: 2,
                            y: 4
                        },
                        {
                            x: 3,
                            y: 2
                        },
                        {
                            x: 4,
                            y: 3
                        },
                        {
                            x: 5,
                            y: 1
                        }
                    ]}
                />

            </>
        );
    }
}
