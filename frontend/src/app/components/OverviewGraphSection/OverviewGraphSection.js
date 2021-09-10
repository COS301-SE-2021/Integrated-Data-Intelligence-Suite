import React from 'react';
import './OverviewGraphSection.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import VictoryBarGraph from '../BarGraph/VictoryBarGraph';
import PieWithCustomLabels from '../PieWithCustomLabels/PieWithCustomLabels';

export default class OverviewGraphSection extends React.Component {
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
                    this.props.text[4].words,
                    this.props.text[5].words,
                    this.props.text[6].words,
                ]
            });
        }
    }

    render() {
        return (
            <>
                <div id={'overview-graph-section'}>
                    <SimpleCard
                        cardTitle="Average Interaction"
                        cardID="overview-graph-metric-1"
                    >
                        <VictoryBarGraph
                            text={this.state.dataToBeDisplayed}
                            key={this.state.dataToBeDisplayed}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Sentiment Distribution"
                        cardID="overview-graph-metric-2"
                    >
                        <PieWithCustomLabels
                            text={this.state.dataToBeDisplayed}
                            key={this.state.dataToBeDisplayed}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Engagement p/ Location"
                        cardID="overview-graph-metric-3"
                    >
                        <VictoryBarGraph
                            text={this.state.dataToBeDisplayed}
                            key={this.state.dataToBeDisplayed}
                        />
                    </SimpleCard>
                </div>
            </>
        );
    }

}
