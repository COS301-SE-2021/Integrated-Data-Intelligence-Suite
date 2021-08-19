import {Menu, Layout} from "antd";
import {BarChartOutlined, HomeOutlined, SettingOutlined} from "@ant-design/icons";
import UserInfoCard from "./UserInfoCard";
import React, {Component} from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import ChartPage from "../../pages/ChartPage/components/ChartPage";
import SettingsPage from "../../pages/SettingsPage/SettingsPage";
import HomePage from "../../pages/HomePage/HomePage";

const {Header, Footer, Sider, Content} = Layout;

export default function SideBar() {
    return (
        <Router>
            <Sider collapsible={false} id={'sidebar_div'}>
                <div id="logo"/>
                <Menu
                    id={'sidebar_menu'}
                    theme="light"
                    defaultSelectedKeys={['1']}
                    mode="inline"
                >
                    <Menu.Item key="1" icon={<HomeOutlined/>}>
                        Home
                        <Link to='/'/>
                    </Menu.Item>

                    <Menu.Item key="2" icon={<BarChartOutlined/>}>
                        Charts
                        <Link to='/chart'/>
                    </Menu.Item>

                    <Menu.Item key="9" icon={<SettingOutlined/>}>
                        Settings
                        <Link to={"/settings"}/>
                    </Menu.Item>
                </Menu>
                <Switch>
                    <Route exact path="/">
                        <HomePage/>
                    </Route>
                    <Route path="/chart">
                        <ChartPage/>
                    </Route>
                    <Route path="/settings">
                        <SettingsPage/>
                    </Route>
                </Switch>
            </Sider>
        </Router>
    );
}


