import React, { Component } from 'react';
import {
    Input,
    Layout,
    Typography,
} from 'antd';
import { Redirect, Route, Switch } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import UserInfoCard from '../../components/UserInfoCard/UserInfoCard';

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

function getLocalUser() {
    const localUser = localStorage.getItem('user');
    if (localUser) {
        // console.log("user logged in is ", localUser)
        return JSON.parse(localUser);
    }
    return null;
}

class HomePage extends Component {
    constructor(props) {
        super(props);
        this.setState({
            user: true,
        });
    }

    render() {
        return (
            <>
                <Switch>
                    <Route exact path="/">
                        <Layout
                            id="outer_layout"
                            className="chart-page"
                        >
                            <SideBar currentPage={'1'}/>

                            <Layout id="inner_layout_div">
                                <Header id="top_bar">
                                    {/* <Title level={1}>Home</Title> */}

                                    <UserInfoCard
                                        name="s"
                                    />
                                </Header>
                            </Layout>

                        </Layout>
                    </Route>
                </Switch>
            </>
        );
    }
}

export default HomePage;
