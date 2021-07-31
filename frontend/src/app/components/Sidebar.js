import Sider from "antd/es/layout/Sider";
import {Menu} from "antd";
import {BarChartOutlined, HomeOutlined, SettingOutlined} from "@ant-design/icons";
import UserInfoCard from "./UserInfoCard";
import React, {Component} from 'react';


class Sidebar extends React.Component {
    state = {
        collapsed: false,
    };

    onCollapse = collapsed => {
        console.log(collapsed);
        this.setState({collapsed});
    };

    render() {
        const {collapsed} = this.state;
        return (
            <Sider collapsible={false} collapsed={collapsed} onCollapse={this.onCollapse} id={'sidebar_div'}>
                <div id="logo"/>
                <Menu theme="light" defaultSelectedKeys={['1']} mode="inline" id={'sidebar_menu'}>
                    <Menu.Item key="1" icon={<HomeOutlined/>}>
                        Home
                    </Menu.Item>
                    <Menu.Item key="2" icon={<BarChartOutlined/>}>
                        Charts
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
                    </Menu.Item>
                </Menu>
                <UserInfoCard/>

            </Sider>
        );
    }
}

export default Sidebar;
// import React, { Component } from 'react';
// import { Link, withRouter } from 'react-router-dom';
//
//
// class Sidebar extends Component {
//     render () {
//         return (
//
//             <nav className="w3-sidebar w3-collapse w3-white w3-animate-left" style={{zIndex:'3', width:'300px'}} id="mySidebar">
//                 <br/>
//                 <div className="w3-container w3-row">
//
//                     <div className="w3-col s8 w3-bar">
//                         <span>Welcome <strong>User</strong></span><br/>
//                     </div>
//                 </div>
//
//                 <hr/>
//                 <div className="w3-container">
//                     <h5>Intelligence Suite</h5>
//                 </div>
//                 <div className="w3-bar-block">
//
//                     <Link to="#">
//                         <a href="#" className="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black"
//                         title="close menu">
//                         <i className="fa fa-remove fa-fw"/> Close Menu</a>
//                     </Link>
//
//                     <Link to="/dashboard">
//                         <a href="../dashboard/Dashboard.js" className="w3-bar-item w3-button w3-padding w3-blue">
//                         <i className="fa fa-users fa-fw"/> Dashboard</a>
//                     </Link>
//
//                     <Link to="/pages/Login">
//                         <a href="../pages/Login.js" className="w3-bar-item w3-button w3-padding">
//                         <i className="fa fa-eye fa-fw"/> Login</a>
//                     </Link>
//
//                     <Link to="/pages/Register">
//                     <a href="../pages/Register.js" className="w3-bar-item w3-button w3-padding">
//                         <i className="fa fa-users fa-fw"/> Register</a>
//                     </Link>
//
//                 </div>
//             </nav>
//         );
//     }
//
//     //slide open/close the side bar with button
// }
//
// export default withRouter(Sidebar);
