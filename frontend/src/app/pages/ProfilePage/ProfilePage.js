import { Divider, Layout, Typography } from 'antd';
import React, { Component, useRef } from 'react';

const { Content, Footer, Header } = Layout;
const { Title } = Typography;

const Profile = () => {
    const message = "Shrey Mandalia";
    return (
        <Layout>
            <Content className="profile-content-section">
                <div className="user-profile">
                    <div className="name-info-card">
                        {message}
                    </div>
                    <div className="rest-of-the-info-card">
                        {message}
                    </div>
                </div>
            </Content>
        </Layout>
    );
};

export default Profile;
