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
import VisxBarStack from '../../components/visxBarStack/visxBarStack';

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

class ChartPage extends Component {
    constructor(props) {
        super(props);
        this.handleTextChange = this.handleTextChange.bind(this);
        this.state = { text: '' };
        this.state.user = getLocalUser();
    }

    // componentDidMount() {
    //     // this.state.text=retrieveData()
    //
    // }

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
                                <SideBar />
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

                                    <Content id="content_section">
                                        <Layout
                                          id="map_card_content_layout_div"
                                          className="map_card"
                                        >
                                            <div
                                              id="indicator-container"
                                            >
                                                <IndicatorCard
                                                  indicatorTitle="Average Sentiment"
                                                  indicatorValue="Very Bad"
                                                  showArrow
                                                  percentChange="-27%"
                                                  graphComponent={(
                                                      <LineGraph
                                                        graphData={LineGraphDataNegative}
                                                        lineColor="#EF062D"
                                                      />
                                                    )}
                                                />

                                                <IndicatorCard
                                                  indicatorTitle="Number of Mentions"
                                                  indicatorValue="246K"
                                                  percentChange="+69%"
                                                  showArrow
                                                  graphComponent={(
                                                      <LineGraph
                                                        graphData={LineGraphDataPositive}
                                                        lineColor="#009966"
                                                      />
                                                    )}
                                                />
                                                <IndicatorCard
                                                  indicatorTitle="Total Sentiment"
                                                  showArrow={false}
                                                  graphComponent={(
                                                      <PieChart
                                                        graphData={PieChartMockData}
                                                      />
                                                    )}
                                                />
                                            </div>

                                            <div id="map-card-container">
                                                <Card
                                                  id="map-card"
                                                  title="World Map"
                                                >
                                                    <MapCard text={this.state.text} />
                                                </Card>

                                                <div id="map-details-container">
                                                    <IndicatorCard
                                                      cardID="top-country-indicator-card"
                                                      indicatorTitle="Engagement by Location"
                                                      showArrow={false}
                                                      graphComponent={(
                                                          <BarGraph
                                                            graphData={BarGraphMockData}
                                                          />
                                                        )}
                                                    />
                                                    <InfoDiv infoValue="77%" />
                                                    <IndicatorCard
                                                      cardID="top-country-indicator-card"
                                                      indicatorTitle="Engagement by Location"
                                                      showArrow={false}
                                                      graphComponent={(
                                                          <BarGraph
                                                            graphData={BarGraphMockData}
                                                          />
                                                        )}
                                                    />
                                                </div>
                                            </div>

                                            {/* <Sider */}
                                            {/*    id="map_card_sidebar" */}
                                            {/*    className="map_card" */}
                                            {/*    style={{ display: 'none' }} */}
                                            {/* > */}
                                            {/*    <DetailsCard/> */}
                                            {/* </Sider> */}
                                        </Layout>

                                        <Layout
                                          id="word-cloud-layout"
                                        >

                                            <div
                                              id="word-cloud-grid-container"
                                            >

                                                <div
                                                  id="word-cloud-item"
                                                  className="word-cloud-grid-item"
                                                >
                                                    <WordCloud />
                                                </div>

                                                <div
                                                  id="stat1-item"
                                                  className="word-cloud-grid-item"
                                                >
                                                    <VisxLineGraph/>
                                                </div>
                                                <div
                                                  id="stat2-item"
                                                  className="word-cloud-grid-item"
                                                >
                                                    locations where its been mentioned
                                                </div>
                                                <div
                                                  id="stat3-item"
                                                  className="word-cloud-grid-item"
                                                >
                                                    timeline thingy
                                                </div>
                                            </div>
                                        </Layout>

                                        {/* The Network Graph */}
                                        <Layout
                                          id="network_graph_layout_div"
                                          className="network_card"
                                        >
                                            <Content
                                              id="network_graph_card_content"
                                              className="network_card"
                                            >
                                                <NetworkGraphCard
                                                  text={this.state.text}
                                                  key={this.state.text}
                                                />
                                            </Content>
                                        </Layout>

                                        {/* The timeline Graph */}
                                        <Layout
                                          id="timeline_graph_div"
                                          className="timeline_card"
                                        >
                                            <Content
                                              id="timeline_graph_card_content"
                                              className="timeline_card"
                                            >
                                                <TimelineGraph
                                                  text={this.state.text}
                                                  key={this.state.text}
                                                />
                                            </Content>
                                        </Layout>
                                    </Content>
                                </Layout>
                            </Layout>
                        </Route>
                    </Switch>
                </>
            );
        }
        return <Redirect to="/login" />;
    }
}

export default ChartPage;
