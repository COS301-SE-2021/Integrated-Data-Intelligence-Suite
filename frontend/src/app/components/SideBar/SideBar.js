import { Menu, Layout } from 'antd';
import {
 BarChartOutlined, HomeOutlined, LaptopOutlined, SettingOutlined,
} from '@ant-design/icons';
import React, { Component } from 'react';
import { BrowserRouter, Link, Router } from 'react-router-dom';
import { AiOutlineHeart, AiOutlineHome } from 'react-icons/all';
import UserInfoCard from '../UserInfoCard/UserInfoCard';

const {
    Header,
    Footer,
    Sider,
    Content,
} = Layout;
const { SubMenu } = Menu;

class SideBar extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            collapsed: false,
            active: '1',
        };

        this.setActive = (value) => {
            this.setState({ active: value });
        };

        this.onCollapse = (collapsed) => {
            console.log(`IsCOllapsed: ${collapsed}`);
            this.setState({ collapsed });
        };
    }

    render() {
        const {
            collapsed,
            active,
        } = this.state;
        console.log('active comp ', active);
        return (
            <>

                <Sider
                  collapsible
                  collapsed={collapsed}
                  onCollapse={this.onCollapse}
                  id="sidebar_div"
                >
                    <div id="logo">
                        logo
                    </div>
                    <Menu
                      id="sidebar_menu"
                      theme="light"
                      defaultSelectedKeys={active}
                      mode="inline"
                    >
                        <Menu.Item
                          key="1"
                          icon={<AiOutlineHome />}
                          onClick={() => this.setActive('1')}
                        >
                            Home
                            <Link to="/" />
                        </Menu.Item>

                        <Menu.Item
                          key="2"
                          icon={<BarChartOutlined />}
                          onClick={() => this.setActive('2')}
                        >
                            Analytics
                            <Link to="/chart" />
                        </Menu.Item>

                        <Menu.Item
                          key="3"
                          icon={<SettingOutlined />}
                          onClick={() => this.setActive('3')}
                        >
                            Settings
                            <Link to="/settings" />
                        </Menu.Item>

                        <Menu.Item
                          key="4"
                          icon={<AiOutlineHeart />}
                          onClick={() => this.setActive('4')}
                        >
                            Credits
                            <Link to="/" />
                        </Menu.Item>

                    </Menu>
                </Sider>
            </>
        );
    }
}

export default SideBar;
