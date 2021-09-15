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
import PieChart from '../../components/PieChart/PieChart';
import '../../components/WordCloud/WordCloud.css';
import WordCloud from '../../components/WordCloud/WordCloud';
import 'rc-slider/assets/index.css';
import SimpleSection from '../../components/SimpleSection/SimpleSection';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import DraggableBarGraph from '../../components/DraggableBarGraph/DraggableBarGraph';
import OverviewSection from '../../components/OverviewSection/OverviewSection';
import GraphWithBrushAndZoom from '../../components/GraphWithBrushAndZoom/GraphWithBrushAndZoom';
import OverviewGraphSection from '../../components/OverviewGraphSection/OverviewGraphSection';

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
                                <SideBar currentPage={'2'}/>
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

                                    <div id="content-section">
                                        <SimpleSection
                                            cardTitle=""
                                            cardID="row-1"
                                        >
                                            <OverviewSection
                                                text={this.state.text}
                                                key={this.state.text}
                                            />
                                        </SimpleSection>

                                        <SimpleSection
                                            cardTitle=""
                                            cardID={'row-2'}
                                        >
                                            <OverviewGraphSection
                                                text={this.state.text}
                                                key={this.state.text}
                                            />
                                        </SimpleSection>

                                        {/*/!**/}
                                        <SimpleSection
                                            cardTitle=""
                                            cardID="row-3"
                                        >
                                            <div id={'location-section'}>
                                                <div id="map-metric-container">
                                                    <SimpleCard
                                                        cardTitle={''}
                                                        cardID={'world-map'}
                                                    >
                                                        <MapCard text={this.state.text}/>
                                                    </SimpleCard>

                                                    <SimpleCard
                                                        cardTitle={'Tweet Frequency'}
                                                        cardID={'map-metric-1'}
                                                    >
                                                        <DraggableBarGraph text={this.state.text}/>
                                                    </SimpleCard>
                                                </div>
                                            </div>
                                        </SimpleSection>

                                        <SimpleSection
                                            cardTitle="Textual Analysis"
                                            cardID="row-4"
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
                                                    cardTitle="Dominant words"
                                                    cardID="word-graph-2"
                                                >
                                                    <PieChart text={this.state.text}/>
                                                </SimpleCard>
                                                <SimpleCard
                                                    cardTitle="Word Sunburst"
                                                    cardID="word-graph-1"
                                                >
                                                    Word Graph1
                                                </SimpleCard>
                                            </div>
                                        </SimpleSection>

                                        <SimpleSection
                                            cardTitle=""
                                            cardID="row-5"
                                        >
                                            <SimpleCard
                                                cardTitle="Relationship Between Entities"
                                                cardID="network-graph-entities"
                                            >
                                                <NetworkGraphCard
                                                    text={this.state.text}
                                                    key={this.state.text}
                                                    indexOfData={11}
                                                />
                                            </SimpleCard>

                                            <SimpleCard
                                                cardTitle="Relationship Between Patterns"
                                                cardID="network-graph-patterns"
                                            >
                                                <NetworkGraphCard
                                                    text={this.state.text}
                                                    key={this.state.text}
                                                    indexOfData={12}
                                                />
                                            </SimpleCard>
                                        </SimpleSection>

                                        <SimpleSection
                                            cardTitle=""
                                            cardID="row-6"
                                        >
                                            <SimpleCard
                                                cardTitle="Timeline"
                                                cardID="anomaly-timeline-card"
                                            >
                                                <TimelineGraph
                                                    text={this.state.text}
                                                    key={this.state.text}
                                                />
                                            </SimpleCard>

                                            <SimpleCard
                                                cardTitle="Scatter Plot"
                                                cardID="anomaly-scatter-plot"
                                            >
                                                Scatter Plot
                                            </SimpleCard>

                                            <SimpleCard
                                                cardTitle="Line Graph"
                                                cardID="anomaly-line-graph"
                                            >
                                                Line Graph
                                            </SimpleCard>
                                        </SimpleSection>
                                        {/**!/*/}
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
