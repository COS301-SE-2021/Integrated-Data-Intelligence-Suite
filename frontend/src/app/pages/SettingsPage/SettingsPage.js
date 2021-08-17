import React, {Component, useRef, useState} from 'react';
import {Layout, Row, Col, Divider} from 'antd';
import { UploadOutlined, UserOutlined, VideoCameraOutlined } from '@ant-design/icons';
import Title from "antd/es/typography/Title";
import SideBar from "../../components/SideBar/SideBar";
import {Link, Route} from "react-router-dom";
import Permissions from "../PermissionsPage/Permissions";
import UserInfoCard from "../../components/SideBar/UserInfoCard";

const { Header, Content, Footer, Sider } = Layout;




const SettingsPage = () => {

    const [component, setComponent] = useState("Permissions");


    return (
        <Layout className={"bodyDiv"}>
            <SideBar/>
            <Layout id={'inner_layout_div'}>
                <Header id={"top_bar"} className={"header"} >
                    {/*<Title level={1}>Settings</Title>*/}
                    <UserInfoCard
                        name="s"
                    />
                </Header>
                <Content id={"settings-container"} className={"outer-container"} style={{ margin: '0' }}>
                    <Row className={"row"}>
                        <Col style={{padding:"30px 20px"}} className={"left-column"} flex="200px">
                            <div className={"option"} onClick={()=>setComponent("Permissions")}>Permissions</div>
                            <Divider />
                            <div className={"option"} onClick={()=>setComponent("Profile")}>Profile</div>
                            <Divider />
                            <div className={"option"} onClick={()=>setComponent("Account")}>Account</div>
                        </Col>
                        <Col style={{padding:"0 0px 30px 0"}} className={"right-column"} flex="auto">
                            { component === "Permissions" && <Permissions/>}
                            { component === "Profile" && <div>Display profile here</div>}
                            { component === "Account" && <div>Manage user account here</div>}
                        </Col>
                    </Row>
                </Content>
            </Layout>
        </Layout>

    );
};
export default SettingsPage;
