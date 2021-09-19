import React, { Component } from 'react';
import {
    Input,
    Layout,
    Typography,
} from 'antd';
import { Redirect, Route, Switch } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import UserInfoCard from '../../components/UserInfoCard/UserInfoCard';
import NavBar from '../../components/NavBar/NavBar';
import Footer from '../../components/Footer/Footer';
import './HomePage.css';
import { SiWindows } from 'react-icons/all';

const {
    Title,
    Text,
} = Typography;
const {
    Header,
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
                        {/*<Layout*/}
                        {/*    id="outer_layout"*/}
                        {/*    className="chart-page"*/}
                        {/*>*/}
                        {/*    <SideBar currentPage={'1'}/>*/}

                        {/*    <Layout id="inner_layout_div">*/}
                        {/*        <Header id="top_bar">*/}
                        {/*            /!* <Title level={1}>Home</Title> *!/*/}

                        {/*            <UserInfoCard*/}
                        {/*                name="s"*/}
                        {/*            />*/}
                        {/*        </Header>*/}
                        {/*    </Layout>*/}
                        {/*</Layout>*/}

                        <div id={'home-page-container'}>
                            <NavBar/>
                            <div id={'home-page-content'}>
                                <div id={'selling-point-1'}>
                                    Data Importing?
                                    <br/>
                                    Data Analysis?
                                    <br/>
                                    Data Visualisation?
                                    <br/>
                                    All in one place.
                                </div>
                                <button id={'download-button'}>
                                    <SiWindows id={'windows-logo'}/>
                                    Download for Windows
                                </button>
                                <div id={'app-image'}>Image</div>
                                <div id={'selling-point-2-container'}>
                                    <div/>
                                    <div id={'selling-point-2'}>
                                        Your own Intelligent Data Suite.
                                        <br/>
                                        Connect your own data sources.
                                        <br/>
                                        Import your own data and visualise it.
                                        <br/>
                                        All with a few clicks.
                                    </div>
                                </div>
                            </div>
                            <Footer/>
                        </div>
                    </Route>
                </Switch>
            </>
        );
    }
}

export default HomePage;
