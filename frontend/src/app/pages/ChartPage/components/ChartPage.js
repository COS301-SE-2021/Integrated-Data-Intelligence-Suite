import React, {Component} from 'react';
import SideBar from "../../../components/SideBar/SideBar";
import {
    Input,
    Layout,
    Card,
    Typography
} from 'antd';
import {Route, Switch} from "react-router-dom";
import MapCard from "./MapCard";
import DetailsCard from "./DetailsCard";
import NetworkGraphCard from "./NetworkGraphCard";
import "../styles/NetworkGraph.css";
import UserInfoCard from "../../../components/SideBar/UserInfoCard";
import SearchBar from "../../../components/SearchBar/SearchBar";
import TimelineGraph from "./TimelineGraph";

const {Title, Text} = Typography;
const {Header, Footer, Sider, Content} = Layout;

class ChartPage extends Component {
    constructor(props){
        super(props);
        this.handleTextChange = this.handleTextChange.bind(this);
        this.state = {text: ''};
    }

    handleTextChange(newText){
        this.setState(({text: newText}));
    }

    render() {
        return (
            <>
                <Switch>
                    <Route exact path='/chart'>
                        <Layout
                            id={'outer_layout'}
                            className={'chart-page'}
                        >
                            <Header id={'top_bar'}>
                                {/*<Title level={1}>Chart Page Title</Title>*/}
                                <SearchBar
                                    text={this.state.text}
                                    handleTextChange={this.handleTextChange}
                                />
                                <UserInfoCard
                                    name="s"
                                />
                            </Header>
                            <Layout id={'inner_layout_div'}>
                            <SideBar/>

                                {/*The Map Graph*/}
                                <Content id={'content_section'}>
                                    <Layout
                                        id={'map_card_content_layout_div'}
                                        className={'map_card'}
                                    >
                                        <Content
                                            id={'map_card_content'}
                                            className={'map_card'}
                                        >
                                            <MapCard text={this.state.text}/>
                                        </Content>

                                        <Sider
                                            id={'map_card_sidebar'}
                                            className={'map_card'}
                                            style={{display:"none"}}
                                        >
                                            {<DetailsCard/>}
                                        </Sider>
                                    </Layout>

                                    {/*The Network Graph*/}
                                    <Layout
                                        id={'network_graph_layout_div'}
                                        className={'network_card'}
                                    >
                                        <Content
                                            id={'network_graph_card_content'}
                                            className={'network_card'}
                                        >
                                            <NetworkGraphCard text={this.state.text}/>
                                        </Content>
                                    </Layout>

                                    {/*The timeline Graph*/}
                                    <Layout
                                        id={'timeline_graph_div'}
                                        className={'timeline_card'}
                                    >
                                        <Content
                                            id={'timeline_graph_card_content'}
                                            className={'timeline_card'}
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

}

export default ChartPage;
