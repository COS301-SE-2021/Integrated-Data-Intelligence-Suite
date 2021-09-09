import React from 'react';
import './OverviewSection.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import UberBarGraph from '../UberBarGraph/UberBarGraph';
import PieChart from '../PieChart/PieChart';

export default class OverviewSection extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <div id={'overview-col-left'}>
                    <SimpleCard
                        cardTitle="Total Likes"
                        cardID="overview-metric-1"
                    >
                        45,541,14
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Trends"
                        cardID="overview-metric-3"
                    >
                        5,413
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Average Sentiment"
                        cardID="overview-metric-2"
                    >
                        bad
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Anomalies"
                        cardID="overview-metric-4"
                    >
                        2100
                    </SimpleCard>
                </div>


                {/*<div*/}
                {/*    id={'overview-col-right'}*/}
                {/*>*/}
                {/*    <SimpleCard*/}
                {/*        cardTitle={'Number of Relationships found'}*/}
                {/*        cardID={'overview-metric-4'}*/}
                {/*    >*/}
                {/*        <UberLineGraph/>*/}
                {/*    </SimpleCard>*/}

                {/*    <SimpleCard*/}
                {/*        cardTitle={'Metric 5'}*/}
                {/*        cardID={'overview-metric-5'}*/}
                {/*    >*/}
                {/*        <PieChart/>*/}
                {/*    </SimpleCard>*/}
                {/*</div>*/}
            </>
        );
    }
}
