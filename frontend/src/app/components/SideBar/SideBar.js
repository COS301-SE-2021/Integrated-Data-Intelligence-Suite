import {Menu, Layout} from "antd";
import {BarChartOutlined, HomeOutlined, SettingOutlined} from "@ant-design/icons";
import UserInfoCard from "./UserInfoCard";
import React, {Component} from 'react';
import {BrowserRouter, Link, Router} from "react-router-dom";

const {Header, Footer, Sider, Content} = Layout;


class SideBar extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            collapsed: false,
        };
    }

    onCollapse = collapsed => {
        console.log("IsCOllapsed: " + collapsed);
        this.setState({collapsed});
    };

    render() {
        const {collapsed} = this.state;
        return (
            <>

                <Sider collapsible={false} collapsed={collapsed} onCollapse={this.onCollapse} id={'sidebar_div'}>
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
                        {/*<SubMenu key="sub1" icon={<UserOutlined/>} title="User">*/}
                        {/*    <Menu.Item key="3">User 1</Menu.Item>*/}
                        {/*    <Menu.Item key="4"> User 2</Menu.Item>*/}
                        {/*</SubMenu>*/}
                        {/*<SubMenu key="sub2" icon={<TeamOutlined/>} title="Team">*/}
                        {/*    <Menu.Item key="6">Team 1</Menu.Item>*/}
                        {/*    <Menu.Item key="8">Team 2</Menu.Item>*/}
                        {/*</SubMenu>*/}
                        <Menu.Item key="9" icon={<SettingOutlined/>}>
                            Settings
                                <Link to={"/settings"}/>
                        </Menu.Item>
                    </Menu>
                </Sider>
            </>
        );
    }
}

export default SideBar;
