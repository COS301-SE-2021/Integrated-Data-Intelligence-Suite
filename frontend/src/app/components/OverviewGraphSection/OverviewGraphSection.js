import React from 'react';
import './OverviewGraphSection.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import VictoryBarGraph from '../BarGraph/VictoryBarGraph';
import PieWithCustomLabels from '../PieWithCustomLabels/PieWithCustomLabels';

export default class OverviewGraphSection extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <div id={'overview-graph-section'}>
                    <SimpleCard
                        cardTitle="Average Interaction"
                        cardID="overview-graph-metric-1"
                    >
                        <VictoryBarGraph/>
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Sentiment Distribution"
                        cardID="overview-graph-metric-2"
                    >
                        <PieWithCustomLabels/>
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Engagement p/ Location"
                        cardID="overview-graph-metric-3"
                    >
                        <VictoryBarGraph/>
                    </SimpleCard>
                </div>
            </>
        );
    }

}
