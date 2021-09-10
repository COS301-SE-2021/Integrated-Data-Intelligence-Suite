import React from 'react';
import './OverviewSection.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import UberBarGraph from '../UberBarGraph/UberBarGraph';
import PieChart from '../PieChart/PieChart';
import ValueWithPercentChange from '../ValueWithPercentChange/ValueWithPercentChange';

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
                        <ValueWithPercentChange
                            isIncreasing
                            rawValue={'1,214,312'}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Trends"
                        cardID="overview-metric-3"
                    >
                        <ValueWithPercentChange
                            isIncreasing={false}
                            rawValue={'23'}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Average Sentiment"
                        cardID="overview-metric-2"
                    >
                        <ValueWithPercentChange
                            isIncreasing={false}
                            rawValue={'bad'}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Anomalies"
                        cardID="overview-metric-4"
                    >
                        <ValueWithPercentChange
                            isIncreasing
                            rawValue={'16'}
                        />
                    </SimpleCard>
                </div>
            </>
        );
    }
}
