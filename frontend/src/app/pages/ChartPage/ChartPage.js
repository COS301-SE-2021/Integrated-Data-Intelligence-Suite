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
import IndicatorLineGraph from '../../components/IndicatorLineGraph/IndicatorLineGraph';

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

                                    {/* The Map Graph */}
                                    <Content id="content_section">
                                        <Layout
                                            id="map_card_content_layout_div"
                                            className="map_card"
                                        >

                                            <div
                                                id={'indicator-container'}
                                            >
                                                <IndicatorCard
                                                    indicatorTitle={'Average Sentiment'}
                                                    indicatorValue={'Very Bad'}
                                                    graphComponent={(
                                                        <IndicatorLineGraph
                                                            graphData={LineGraphDataNegative}
                                                        />
                                                    )}
                                                />


                                                <IndicatorCard
                                                    indicatorTitle={'Number of Mentions'}
                                                    indicatorValue={'246K'}
                                                    graphComponent={(
                                                        <IndicatorLineGraph
                                                            graphData={LineGraphDataPositive}
                                                        />
                                                    )}
                                                />
                                                <IndicatorCard
                                                    indicatorTitle={'Statistic 3'}
                                                    indicatorValue={'246K'}
                                                />
                                            </div>

                                            <Card
                                                // title="Geo location analysis"
                                                style={{
                                                    width: '100%',
                                                    padding: 0
                                                }}

                                            >
                                                <MapCard text={this.state.text}/>


                                                {/*<div*/}
                                                {/*    style={{*/}
                                                {/*        width: '20%',*/}
                                                {/*        height: '300px',*/}
                                                {/*        float: 'left',*/}
                                                {/*        border: '3px solid blue',*/}
                                                {/*    }}*/}
                                                {/*>*/}
                                                {/*    right Sided*/}
                                                {/*</div>*/}

                                            </Card>

                                            {/* <Sider */}
                                            {/*    id="map_card_sidebar" */}
                                            {/*    className="map_card" */}
                                            {/*    style={{ display: 'none' }} */}
                                            {/* > */}
                                            {/*    <DetailsCard/> */}
                                            {/* </Sider> */}
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
        return <Redirect to="/login"/>;
    }
}

export default ChartPage;
