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
            user: null,
        });
    }

    render() {
        if (true) {
            return <Redirect to="/chart" />;
        }
        if (this.state.user) {
            return (
                <>
                    <Switch>
                        <Route exact path="/">
                            <Layout
                              id="outer_layout"
                              className="chart-page"
                            >
                                <Header id="top_bar">
                                    {/* <Title level={1}>Home</Title> */}

                                    <UserInfoCard
                                      name="s"
                                    />
                                </Header>
                                <Layout id="inner_layout_div">
                                    <SideBar />
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

export default HomePage;
