/* eslint-disable */
import React, {Component} from 'react';
import {Menu, Layout} from 'antd';
import {
    BarChartOutlined,
    SettingOutlined
} from '@ant-design/icons';
import {BrowserRouter, Link, Router} from 'react-router-dom';

import {
    AiOutlineHeart,
    AiOutlineHome, FiSettings, GoGraph, GrGraphQl, HiOutlineCube,
    HiOutlineDocumentReport
} from 'react-icons/all';
import UserInfoCard from "../UserInfoCard/UserInfoCard";

const {
    Header,
    Footer,
    Sider,
    Content,
} = Layout;
const {SubMenu} = Menu;

class SideBar extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            collapsed: false,
            active: 2,
        };

        this.setActive = (value) => {
            this.setState({active: value});
        };

        this.onCollapse = (collapsed) => {
            console.log(`IsCOllapsed: ${collapsed}`);
            this.setState({collapsed});
        };
    }

    componentDidMount() {
        // this.setActive(this.props.currentPage);
        this.setState({active: this.props.currentPage});
    }

    render() {
        const {
            collapsed,
            active,
        } = this.state;
        return (
            <>
                <Sider
                    collapsible={false}
                    collapsed={collapsed}
                    onCollapse={this.onCollapse}
                    id="sidebar_div"
                >
                    <div id="logo"/>
                    <Menu
                        id="sidebar_menu"
                        theme="light"
                        defaultSelectedKeys={this.props.currentPage}
                        mode="inline"
                    >
                        <Menu.Item
                            key="2"
                            icon={<GoGraph
                                className={'sidebar-icon'}
                            />}
                            onClick={() => this.setActive('2')}
                        >
                            Analytics
                            <Link to="/chart"/>
                        </Menu.Item>

                        <Menu.Item
                            key="5"
                            icon={<HiOutlineDocumentReport
                                className={'sidebar-icon'}
                            />}
                            onClick={() => this.setActive('5')}
                        >
                            Reports
                            <Link to="/reports"/>
                        </Menu.Item>

                        <Menu.Item
                            key="6"
                            icon={<HiOutlineCube
                                className={'sidebar-icon'}
                            />}
                            onClick={() => this.setActive('6')}
                        >
                            Models
                            <Link to="/manageModels"/>
                        </Menu.Item>

                        <Menu.Item
                            key="3"
                            icon={<FiSettings
                                className={'sidebar-icon'}
                            />}
                            onClick={() => this.setActive('3')}
                        >
                            Settings
                            <Link to="/settings"/>
                        </Menu.Item>

                        {/*<Menu.Item*/}
                        {/*    key="4"*/}
                        {/*    icon={<AiOutlineHeart*/}
                        {/*        className={'sidebar-icon'}*/}
                        {/*    />}*/}
                        {/*    onClick={() => this.setActive('4')}*/}
                        {/*>*/}
                        {/*    Credits*/}
                        {/*    <Link to="/credits"/>*/}
                        {/*</Menu.Item>*/}
                    </Menu>
                    <UserInfoCard/>

                </Sider>
            </>
        );
    }
}

export default SideBar;
