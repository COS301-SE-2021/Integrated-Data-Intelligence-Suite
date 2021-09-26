import React from 'react';
import './OverviewSection.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import UberBarGraph from '../UberBarGraph/UberBarGraph';
import PieChart from '../PieChart/PieChart';
import ValueWithPercentChange from '../ValueWithPercentChange/ValueWithPercentChange';

let num_of_anomalies = null;
let overall_sentiment = null;
let trends_value = null;
let total_likes = null;

export default class OverviewSection extends React.Component {

    constructor(props) {
        super(props);
        this.state = { dataToBeDisplayed: [] };
    }

    componentDidMount() {
        // //Adding nodes to the layout
        if (typeof this.props.text[0] === 'undefined') {
            // some error message
            this.setState({ dataToBeDisplayed: [] });
            console.log('array is undefined');
        } else if (this.props.text[0].length === 0) {
            // Some error
            this.setState({ dataToBeDisplayed: [] });
            console.log('array is empty');
        } else if (this.props.text[0].length > 0) {
            console.log('myron im here');
            console.log(this.state.dataToBeDisplayed);
            this.setState({
                dataToBeDisplayed: [
                    this.props.text[0],
                    this.props.text[1],
                    this.props.text[2],
                    this.props.text[3]
                ]
            });
            total_likes = this.props.text[0][0].words;
            overall_sentiment = this.props.text[1][0].words;
            num_of_anomalies = this.props.text[3][0].words;
            trends_value = this.props.text[2][0].words;
        }
    }

    render() {
        return (
            <>
                <div id={'overview-col-left'}>
                    <SimpleCard
                        cardTitle="Total Interaction"
                        cardID="overview-metric-1"
                        titleOnTop
                    >
                        <ValueWithPercentChange
                            isIncreasing
                            rawValue={total_likes}
                            key={total_likes}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Trends"
                        cardID="overview-metric-3"
                        titleOnTop
                    >
                        <ValueWithPercentChange
                            isIncreasing={false}
                            rawValue={trends_value}
                            key={total_likes}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Overall Sentiment"
                        cardID="overview-metric-2"
                        titleOnTop
                    >
                        <ValueWithPercentChange
                            isIncreasing={false}
                            rawValue={overall_sentiment}
                            key={total_likes}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Anomalies"
                        cardID="overview-metric-4"
                        titleOnTop
                    >
                        <ValueWithPercentChange
                            isIncreasing
                            rawValue={num_of_anomalies}
                            key={total_likes}
                        />
                    </SimpleCard>
                </div>
            </>
        );
    }
}
