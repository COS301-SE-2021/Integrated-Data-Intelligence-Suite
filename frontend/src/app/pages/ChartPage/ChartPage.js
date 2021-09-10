import React, { Component } from 'react';
import {
    Input,
    Layout,
    Card,
    Typography,
} from 'antd';
import { Redirect, Route, Switch } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import MapCard from '../../components/MapCard/MapCard';
import DetailsCard from '../../components/DetailsCard/DetailsCard';
import NetworkGraphCard from '../../components/NetworkGraph/NetworkGraphCard';
import '../../components/NetworkGraph/NetworkGraph.css';
import UserInfoCard from '../../components/UserInfoCard/UserInfoCard';
import SearchBar from '../../components/SearchBar/SearchBar';
import TimelineGraph from '../../components/TimelineGraph/TimelineGraph';
import useGet from '../../functions/useGet';
import IndicatorCard from '../../components/IndicatorCard/IndicatorCard';
import LineGraphDataPositive from '../../Mocks/LineGraphDataPositive.json';
import LineGraphDataNegative from '../../Mocks/LineGraphDataNegative.json';
import LineGraph from '../../components/LineGraph/LineGraph';
import PieChart from '../../components/PieChart/PieChart';
import PieChartMockData from '../../Mocks/PieChartDataMock.json';
import BarGraphMockData from '../../Mocks/BarGraphDataMock.json';
import BarGraph from '../../components/BarGraph/BarGraph';
import InfoDiv from '../../components/InfoDiv/InfoDiv';
import '../../components/WordCloud/WordCloud.css';
import WordCloud from '../../components/WordCloud/WordCloud';
import VisxLineGraph from '../../components/vsixLineGraph/VisxLineGraph';
import VisxBarStackGraph from '../../components/visxBarStackGraph/visxBarStackGraph';
import VisxAreaGraph from '../../components/visxAreaGraph/visxAreaGraph';
import VisxAreaStackGraph from '../../components/visxAreaStackGraph/visxAreaStackGraph';
import VisxBarGraph from '../../components/visxBarGraph/visxBarGraph';
import VisxBarGroupGraph from '../../components/visxBarGroupGraph/visxBarGroup';
import visxAreaMockData from '../../Mocks/vsixAreaDataMock.json';
import Slider, { Range } from 'rc-slider';
import 'rc-slider/assets/index.css';
import SimpleSection from '../../components/SimpleSection/SimpleSection';
import AreaGraph from '../../components/AreaGraph/AreaGraph';
import { Simple } from 'leaflet/src/geo/crs/CRS.Simple';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import UberLineGraph from '../../components/UberLineGraph/UberLineGraph';
import UberSunburstGraph from '../../components/UberSunburstGraph/UberSunburstGraph';
import DraggableBarGraph from '../../components/DraggableBarGraph/DraggableBarGraph';
import OverviewSection from '../../components/OverviewSection/OverviewSection';
import GraphWithBrushAndZoom from '../../components/GraphWithBrushAndZoom/GraphWithBrushAndZoom';

const {
    Title,
    Text,
} = Typography;
const {
    Header,
    Footer,
    Sider,
    Content,
} = Layout;

function retrieveData() {
    fetch('/retrievePrevious')
        .then((res) => {
            if (!res.ok) {
                return null;
            }
            return res.json();
        });
}

function getLocalUser() {
    const localUser = localStorage.getItem('user');
    if (localUser) {
        console.log('user logged in is ', localUser);
        return JSON.parse(localUser);
    }
    return null;
}

function getRandomSeriesData(total) {
    const result = [];
    let lastY = Math.random() * 40 - 20;
    let y;
    const firstY = lastY;
    for (let i = 0; i < Math.max(total, 3); i++) {
        y = Math.random() * firstY - firstY / 2 + lastY;
        result.push({
            left: i,
            top: y,
        });
        lastY = y;
    }
    return result;
}

class ChartPage extends Component {
    constructor(props) {
        super(props);
        this.handleTextChange = this.handleTextChange.bind(this);
        this.state = { text: '' };
        this.state.user = getLocalUser();
    }

    handleTextChange(newText) {
        this.setState(({ text: newText }));
    }

    render() {
        if (this.state.user) {
            return (
                <>
                    <Switch>
                        <Route exact path="/chart">
                            <Layout
                                id="outer_layout"
                                className="chart-page"
                            >
                                <SideBar/>
                                <Layout id="inner_layout_div">
                                    <Header id="top_bar">
                                        {/* <Title level={1}>Chart Page Title</Title> */}
                                        <SearchBar
                                            text={this.state.text}
                                            handleTextChange={this.handleTextChange}
                                        />
                                        <UserInfoCard
                                            name="s"
                                        />
                                    </Header>

                                    {/* <Content id="content_section"> */}
                                    {/*    /!* <Layout *!/ */}
                                    {/*    /!*    id="map_card_content_layout_div" *!/ */}
                                    {/*    /!*    className="map_card" *!/ */}
                                    {/*    /!* > *!/ */}
                                    {/*    /!* <div *!/ */}
                                    {/*    /!*    id="indicator-container" *!/ */}
                                    {/*    /!* > *!/ */}
                                    {/*    <IndicatorCard */}
                                    {/*        indicatorTitle="Average Sentiment" */}
                                    {/*        indicatorValue="Very Bad" */}
                                    {/*        showArrow */}
                                    {/*        percentChange="-27%" */}
                                    {/*        cardID="ave-sentiment-card" */}
                                    {/*        graphComponent={( */}
                                    {/*            <VisxAreaGraph */}
                                    {/*                graphData={this.state.text} */}
                                    {/*                text={this.state.text} */}
                                    {/*                key={this.state.text} */}
                                    {/*                showSecondLine={false} */}
                                    {/*                showXAxis={false} */}
                                    {/*                showYAxis={false} */}
                                    {/*                idName="ave-sentiment-area-graph" */}
                                    {/*                tooltipKeyColor="red" */}
                                    {/*            /> */}
                                    {/*        )} */}
                                    {/*    /> */}

                                    {/*    <IndicatorCard */}
                                    {/*        indicatorTitle="Number of Mentions" */}
                                    {/*        indicatorValue="246K" */}
                                    {/*        percentChange="+69%" */}
                                    {/*        cardID="num-mentions-card" */}
                                    {/*        showArrow */}
                                    {/*        graphComponent={( */}
                                    {/*            <VisxAreaGraph */}
                                    {/*                graphData={this.state.text} */}
                                    {/*                text={this.state.text} */}
                                    {/*                key={this.state.text} */}
                                    {/*                showSecondLine={false} */}
                                    {/*                showXAxis={false} */}
                                    {/*                showYAxis={false} */}
                                    {/*                idName="num-mentions-area-graph" */}
                                    {/*                tooltipKeyColor="green" */}
                                    {/*            /> */}
                                    {/*        )} */}
                                    {/*    /> */}

                                    {/*    <IndicatorCard */}
                                    {/*        indicatorTitle="Total Sentiment" */}
                                    {/*        showArrow={false} */}
                                    {/*        cardID="total-sentiment-card" */}
                                    {/*        graphComponent={( */}
                                    {/*            <PieChart */}
                                    {/*                graphData={this.state.text} */}
                                    {/*                text={this.state.text} */}
                                    {/*                key={this.state.text} */}
                                    {/*                legendOrientation="horizontal" */}
                                    {/*                pieID="total-sentiment-pie" */}
                                    {/*                legendID="total-sentiment-legend" */}
                                    {/*            /> */}
                                    {/*        )} */}
                                    {/*    /> */}

                                    {/*    <IndicatorCard */}
                                    {/*        indicatorTitle="Sentiment vs Time" */}
                                    {/*        showArrow={false} */}
                                    {/*        cardID="sentiment-vs-time-card" */}
                                    {/*        graphComponent={( */}
                                    {/*            <VisxLineGraph/> */}
                                    {/*        )} */}
                                    {/*    /> */}

                                    {/*    /!* </div> *!/ */}

                                    {/*    <IndicatorCard */}
                                    {/*        indicatorTitle="Engagement By Location" */}
                                    {/*        showArrow={false} */}
                                    {/*        cardID="engagement-location-card" */}
                                    {/*        graphComponent={( */}
                                    {/*            <VisxBarStackGraph/> */}
                                    {/*        )} */}
                                    {/*    /> */}

                                    {/*    /!*<IndicatorCard*!/ */}
                                    {/*    /!*    indicatorTitle="Tweet Content"*!/ */}
                                    {/*    /!*    showArrow={false}*!/ */}
                                    {/*    /!*    cardID="tweet-content-card"*!/ */}
                                    {/* /> */}

                                    {/*    <IndicatorCard */}
                                    {/*        indicatorTitle="Frequency of Entity Type" */}
                                    {/*        showArrow={false} */}
                                    {/*        cardID="freq-entity-type" */}
                                    {/*        graphComponent={( */}
                                    {/*            <VisxBarGroupGraph/> */}
                                    {/*        )} */}
                                    {/*    /> */}

                                    {/*    <IndicatorCard */}
                                    {/*        indicatorTitle="Frequency per country" */}
                                    {/*        showArrow={false} */}
                                    {/*        // cardID={'total-sentiment-pie-card'} */}
                                    {/*        graphComponent={( */}
                                    {/*            <VisxBarStackGraph/> */}
                                    {/*        )} */}
                                    {/*    /> */}

                                    {/*    <div id="map-card-container"> */}
                                    {/*        <Card */}
                                    {/*            id="map-card" */}
                                    {/*            // title="World Map" */}
                                    {/*        > */}
                                    {/*            <MapCard text={this.state.text}/> */}
                                    {/*        </Card> */}

                                    {/*        /!* <div id="map-details-container"> *!/ */}
                                    {/*        /!* </div> *!/ */}
                                    {/*    </div> */}
                                    {/*    /!* <IndicatorCard *!/ */}
                                    {/*    /!*    indicatorTitle="Some Title" *!/ */}
                                    {/*    /!*    cardID="top-country-indicator-card" *!/ */}
                                    {/*    /!*    showArrow={false} *!/ */}
                                    {/*    /!*    graphComponent={( *!/ */}
                                    {/*    /!*        <BarGraph *!/ */}
                                    {/*    /!*            graphData={BarGraphMockData} *!/ */}
                                    {/*    /!*        /> *!/ */}
                                    {/*    /!*    )} *!/ */}
                                    {/*    /!* /> *!/ */}
                                    {/*    /!* <InfoDiv infoValue="77%"/> *!/ */}
                                    {/*    /!* <IndicatorCard *!/ */}
                                    {/*    /!*    cardID="top-country-indicator-card" *!/ */}
                                    {/*    /!*    indicatorTitle="Some Title" *!/ */}
                                    {/*    /!*    showArrow={false} *!/ */}
                                    {/*    /!*    graphComponent={( *!/ */}
                                    {/*    /!*        <BarGraph *!/ */}
                                    {/*    /!*            graphData={BarGraphMockData} *!/ */}
                                    {/*    /!*        /> *!/ */}
                                    {/*    /!*    )} *!/ */}
                                    {/*    /!* /> *!/ */}

                                    {/*    /!* <Sider *!/ */}
                                    {/*    /!*    id="map_card_sidebar" *!/ */}
                                    {/*    /!*    className="map_card" *!/ */}
                                    {/*    /!*    style={{ display: 'none' }} *!/ */}
                                    {/*    /!* > *!/ */}
                                    {/*    /!*    <DetailsCard/> *!/ */}
                                    {/*    /!* </Sider> *!/ */}
                                    {/*    /!* </Layout> *!/ */}

                                    {/*    <Layout */}
                                    {/*        id="word-cloud-layout" */}
                                    {/*    > */}

                                    {/*        <div */}
                                    {/*            id="word-cloud-grid-container" */}
                                    {/*        > */}
                                    {/*            <div */}
                                    {/*                id="word-cloud-item" */}
                                    {/*                className="word-cloud-grid-item" */}
                                    {/*            > */}
                                    {/*                <WordCloud */}
                                    {/*                    text={this.state.text} */}
                                    {/*                    key={this.state.text} */}
                                    {/*                /> */}
                                    {/*            </div> */}

                                    {/*            <div */}
                                    {/*                id="stat1-item" */}
                                    {/*                className="word-cloud-grid-item" */}
                                    {/*            > */}
                                    {/*                <VisxBarGroupGraph/> */}
                                    {/*            </div> */}
                                    {/*            <div */}
                                    {/*                id="stat2-item" */}
                                    {/*                className="word-cloud-grid-item" */}
                                    {/*            > */}
                                    {/*                locations where its been mentioned */}
                                    {/*            </div> */}
                                    {/*            <div */}
                                    {/*                id="stat3-item" */}
                                    {/*                className="word-cloud-grid-item" */}
                                    {/*            > */}
                                    {/*                timeline thingy */}
                                    {/*            </div> */}
                                    {/*        </div> */}
                                    {/*    </Layout> */}

                                    {/*    /!* The Network Graph *!/ */}
                                    {/*    <Layout */}
                                    {/*        id="network_graph_layout_div" */}
                                    {/*        className="network_card" */}
                                    {/*    > */}
                                    {/*        <Content */}
                                    {/*            id="network_graph_card_content" */}
                                    {/*            className="network_card" */}
                                    {/*        > */}
                                    {/*            <NetworkGraphCard */}
                                    {/*                text={this.state.text} */}
                                    {/*                key={this.state.text} */}
                                    {/*            /> */}
                                    {/*        </Content> */}
                                    {/*    </Layout> */}

                                    {/*    /!* The timeline Graph *!/ */}
                                    {/*    <Layout */}
                                    {/*        id="timeline_graph_div" */}
                                    {/*        className="timeline_card" */}
                                    {/*    > */}
                                    {/*        <Content */}
                                    {/*            id="timeline_graph_card_content" */}
                                    {/*            className="timeline_card" */}
                                    {/*        > */}
                                    {/*            <TimelineGraph */}
                                    {/*                text={this.state.text} */}
                                    {/*                key={this.state.text} */}
                                    {/*            /> */}
                                    {/*        </Content> */}
                                    {/*    </Layout> */}
                                    {/* </Content> */}
                                    <div id="content-section">
                                        <SimpleSection
                                            cardTitle=""
                                            cardID="row-1"
                                        >
                                            <OverviewSection/>
                                        </SimpleSection>

                                        <SimpleSection
                                            cardTitle=""
                                            cardID={"row-1.5"}
                                        >
                                            xxx
                                        </SimpleSection>

                                        <SimpleSection
                                            cardTitle=""
                                            cardID="row-2"
                                        >
                                            <div id={'location-section'}>
                                                <div id="map-metric-container">
                                                    <SimpleCard
                                                        cardTitle={'World Map'}
                                                        cardID={'world-map'}
                                                    >
                                                        <MapCard text={this.state.text}/>
                                                    </SimpleCard>

                                                    <SimpleCard
                                                        cardTitle={'Map Metric 1'}
                                                        cardID={'map-metric-1'}
                                                    >
                                                        <DraggableBarGraph/>
                                                    </SimpleCard>
                                                </div>
                                                <SimpleCard
                                                    cardTitle="Map Metric 2"
                                                    cardID="map-metric-2"
                                                >
                                                    <GraphWithBrushAndZoom/>
                                                </SimpleCard>
                                            </div>
                                        </SimpleSection>

                                        <SimpleSection
                                            cardTitle="Textual Analysis"
                                            cardID="row-3"
                                        >
                                            <SimpleCard
                                                cardTitle="Word Cloud"
                                                cardID="word-cloud-card"
                                            >
                                                <WordCloud
                                                    text={this.state.text}
                                                    key={this.state.text}
                                                />
                                            </SimpleCard>

                                            <div id="word-cloud-graph-container">
                                                <SimpleCard
                                                    cardTitle="word-graph-1"
                                                    cardID="word-graph-1"
                                                >
                                                    Word Graph1
                                                </SimpleCard>

                                                <SimpleCard
                                                    cardTitle="word-graph-2"
                                                    cardID="word-graph-2"
                                                >
                                                    Word Graph 2
                                                </SimpleCard>
                                            </div>
                                        </SimpleSection>

                                        <SimpleSection
                                            cardTitle="Network"
                                            cardID="row-4"
                                        />
                                    </div>
                                </Layout>
                            </Layout>
                        </Route>
                    </Switch>
                </>
            );
        }
        return (
            <>
                <Redirect to="/login"/>
            </>
        );
    }
}

export default ChartPage;
