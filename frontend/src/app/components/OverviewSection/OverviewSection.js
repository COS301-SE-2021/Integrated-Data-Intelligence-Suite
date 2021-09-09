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
                        cardTitle="Number Of Mentions"
                        cardID="overview-metric-1"
                    >
                        <UberBarGraph/>
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Locations Engaged"
                        cardID="overview-metric-2"
                    >
                        <UberBarGraph/>
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Overall Sentiment"
                        cardID="overview-metric-3"
                    >
                        <PieChart/>
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
