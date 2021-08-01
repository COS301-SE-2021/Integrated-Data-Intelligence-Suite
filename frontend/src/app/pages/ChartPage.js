import React, {Component} from 'react';
import SideBar from "../components/SideBar/SideBar";
import {
    Input, Layout
} from 'antd';
import {Typography} from 'antd';
import {Route, Switch} from "react-router-dom";

const {Title, Text} = Typography;
const {Header, Footer, Sider, Content} = Layout;


class ChartPage extends Component {
    state = {}

    render() {
        return (
            <>
                <Switch>
                    <Route exact path='/chart'>
                        <Layout id={'outer_layout'} className={'chart-page'}>
                            <SideBar/>
                            <Layout>
                                <Header id={'top_bar'}>
                                    {/*<SearchBar/>*/}
                                    <Title level={1}>ChartPage</Title>
                                </Header>
                                <Content id={'content_section'}>Content</Content>
                                {/*<Footer id={'footer_section'}>Footer</Footer>*/}
                            </Layout>
                        </Layout>
                    </Route>
                </Switch>
            </>
        );
    }

}

export default ChartPage;
