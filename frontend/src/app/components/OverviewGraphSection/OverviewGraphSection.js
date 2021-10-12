import React from 'react';
import './OverviewGraphSection.css';
import { useRecoilValue } from 'recoil';
import SimpleCard from '../SimpleCard/SimpleCard';
import VictoryBarGraph from '../BarGraph/VictoryBarGraph';
import PieWithCustomLabels from '../PieWithCustomLabels/PieWithCustomLabels';
import {
    averageInteractionState,
    engagementPerProvinceState,
    sentimentDistributionState,
} from '../../assets/AtomStore/AtomStore';

const OverviewGraphSection = () => {
    const averageInteraction = useRecoilValue(averageInteractionState);
    const sentimentDistribution = useRecoilValue(sentimentDistributionState);
    const engagementData = useRecoilValue(engagementPerProvinceState);

    return (
        <>
            <div id="overview-graph-section">
                <SimpleCard
                  cardTitle="Average Interaction for each trend"
                  cardID="overview-graph-metric-1"
                  titleOnTop
                >
                    {
                        averageInteraction &&
                        (
                            <VictoryBarGraph
                              text={averageInteraction}
                              key={averageInteraction}
                              xAxisLabel=""
                              yAxisLabel=""
                              showYAxisLabel={false}
                              showXAxisLabel
                            />
                        )
                    }
                </SimpleCard>

                <SimpleCard
                  cardTitle="Sentiment Distribution"
                  cardID="overview-graph-metric-2"
                  titleOnTop
                >
                    {
                        sentimentDistribution &&
                        (
                            <PieWithCustomLabels
                              text={sentimentDistribution}
                              key={sentimentDistribution}
                            />
                        )
                    }
                </SimpleCard>

                <SimpleCard
                  cardTitle="Engagement per Province"
                  cardID="overview-graph-metric-3"
                  titleOnTop
                >
                    {
                        engagementData &&
                        (
                            <VictoryBarGraph
                              text={engagementData}
                              key={engagementData}
                              xAxisLabel=""
                              yAxisLabel=""
                            />
                        )
                    }
                </SimpleCard>
            </div>
        </>
    );
};

export default OverviewGraphSection;
