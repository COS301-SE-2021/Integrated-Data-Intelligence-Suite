import React from 'react';
import { VictoryLabel, VictoryPie, VictoryTooltip } from 'victory';
import PieCustomLabel from '../PieCustomLabel/PieCustomLabel';

export default class PieWithCustomLabels extends React.Component {
    constructor(props) {
        super(props);
    }

    _dataToDisplay(data_from_backend) {
        console.log('pie chart with custom label');
        console.log(data_from_backend);
        return data_from_backend;
    }

    render() {
        return (
            <>
                <VictoryPie
                    animate={{
                        duration: 2000
                    }}
                    style={{ labels: { fill: 'white' } }}
                    innerRadius={100}
                    // labelRadius={120}
                    // labels={({ datum }) => `#${datum.y}`}
                    labelComponent={<PieCustomLabel/>}
                    data={this._dataToDisplay(this.props.text)}
                    colorScale={['green', 'red']}
                />
            </>
        );
    }
}
