import React from 'react';
import './OverviewSection.css';
import { useRecoilValue } from 'recoil';
import SimpleCard from '../SimpleCard/SimpleCard';
import ValueWithPercentChange from '../ValueWithPercentChange/ValueWithPercentChange';
import {
    numberOfAnomaliesState,
    numberOfTrendsState, overallSentimentState,
    totalLikedState,
} from '../../assets/AtomStore/AtomStore';

export default function OverviewSection() {
    const totalLikes = useRecoilValue(totalLikedState);
    const overallSentiment = useRecoilValue(overallSentimentState);
    const trends = useRecoilValue(numberOfTrendsState);
    const anomalies = useRecoilValue(numberOfAnomaliesState);

    return (
        <>
            <div id="overview-col-left">
                <SimpleCard
                  cardTitle="Total Interaction"
                  cardID="overview-metric-1"
                  titleOnTop
                >
                    {
                        totalLikes &&
                        (
                            <ValueWithPercentChange
                              rawValue={totalLikes}
                            />
                        )
                    }
                </SimpleCard>

                <SimpleCard
                  cardTitle="Trends"
                  cardID="overview-metric-3"
                  titleOnTop
                >
                    {
                        trends &&
                        (
                            <ValueWithPercentChange
                              isIncreasing={false}
                              rawValue={trends}
                              key={totalLikes}
                            />
                        )
                    }
                </SimpleCard>

                <SimpleCard
                  cardTitle="Overall Sentiment"
                  cardID="overview-metric-2"
                  titleOnTop
                >
                    {
                        overallSentiment &&
                        (
                            <ValueWithPercentChange
                              isIncreasing={false}
                              rawValue={overallSentiment}
                              key={totalLikes}
                            />
                        )
                    }
                </SimpleCard>

                <SimpleCard
                  cardTitle="Anomalies"
                  cardID="overview-metric-4"
                  titleOnTop
                >
                    {
                        anomalies &&
                        (
                            <ValueWithPercentChange
                              isIncreasing
                              rawValue={anomalies}
                              key={totalLikes}
                            />
                        )
                    }
                </SimpleCard>
            </div>
        </>
    );
}
