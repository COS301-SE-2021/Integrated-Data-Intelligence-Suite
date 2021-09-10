import React from 'react';
import './OverviewGraphSection.css';
import SimpleCard from '../SimpleCard/SimpleCard';

export default class OverviewGraphSection extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <div id={'overview-graph-section'}>
                    <SimpleCard
                        cardTitle="Total Likes"
                        cardID="overview-metric-1"
                    >

                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Trends"
                        cardID="overview-metric-3"
                    >

                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Average Sentiment"
                        cardID="overview-metric-2"
                    >

                    </SimpleCard>
                </div>
            </>
        );
    }

}
