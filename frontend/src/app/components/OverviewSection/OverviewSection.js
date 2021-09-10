import React from 'react';
import './OverviewSection.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import UberBarGraph from '../UberBarGraph/UberBarGraph';
import PieChart from '../PieChart/PieChart';
import ValueWithPercentChange from '../ValueWithPercentChange/ValueWithPercentChange';

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
            // this.theData = this.props.text[1];
            this.setState({
                dataToBeDisplayed: [
                    this.props.text[0].words,
                    this.props.text[1].words,
                    this.props.text[2].words,
                    this.props.text[3].words
                ]
            });
        }
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
                            // rawValue={'1,214,312'}
                            rawValue={this.state.dataToBeDisplayed[0]}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Trends"
                        cardID="overview-metric-3"
                    >
                        <ValueWithPercentChange
                            isIncreasing={false}
                            // rawValue={'23'}
                            rawValue={this.state.dataToBeDisplayed[2]}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Average Sentiment"
                        cardID="overview-metric-2"
                    >
                        <ValueWithPercentChange
                            isIncreasing={false}
                            // rawValue={'bad'}
                            rawValue={this.state.dataToBeDisplayed[1]}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Anomalies"
                        cardID="overview-metric-4"
                    >
                        <ValueWithPercentChange
                            isIncreasing
                            // rawValue={'16'}
                            rawValue={this.state.dataToBeDisplayed[3]}
                        />
                    </SimpleCard>
                </div>
            </>
        );
    }
}
