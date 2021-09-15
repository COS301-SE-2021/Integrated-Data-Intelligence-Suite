import React from 'react';
import './OverviewGraphSection.css';
import SimpleCard from '../SimpleCard/SimpleCard';
import VictoryBarGraph from '../BarGraph/VictoryBarGraph';
import PieWithCustomLabels from '../PieWithCustomLabels/PieWithCustomLabels';

let _average_interaction = null;
let _pie_chart_data = null;
let _engagement_data = null;
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
            _average_interaction = this.props.text[4];
            _pie_chart_data = this.props.text[5];
            _engagement_data = this.props.text[6];
            console.log(this.props);
            console.log(_engagement_data);
        }
    }

    render() {
        return (
            <>
                <div id={'overview-graph-section'}>
                    <SimpleCard
                        cardTitle="Average Interaction for each trend"
                        cardID="overview-graph-metric-1"
                    >
                        <VictoryBarGraph
                            text={_average_interaction}
                            key={_average_interaction}
                            xAxisLabel={'Trend'}
                            yAxisLabel={''}
                            showYAxisLabel={false}
                            showXAxisLabel
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Sentiment Distribution"
                        cardID="overview-graph-metric-2"
                    >
                        <PieWithCustomLabels
                            text={_pie_chart_data}
                            key={_pie_chart_data}
                        />
                    </SimpleCard>

                    <SimpleCard
                        cardTitle="Engagement per Location"
                        cardID="overview-graph-metric-3"
                    >
                        <VictoryBarGraph
                            text={_engagement_data}
                            key={_engagement_data}
                            xAxisLabel={'Provinces'}
                            yAxisLabel={'Engagement'}
                        />
                    </SimpleCard>
                </div>
            </>
        );
    }

}
