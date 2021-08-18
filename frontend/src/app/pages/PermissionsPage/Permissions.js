import UserList from "../../components/ContentSection/UserList";
import SideBar from "../../components/SideBar/SideBar";
import {Divider, Layout} from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import Title from "antd/es/typography/Title";
import useGet from "../../functions/useGet";
import React, {Component, useRef} from 'react';



const Permissions = () => {
    const { data:users, isPending, error } = useGet('/user/getAll');

    return (
        <Layout>
            <Content className={"permissions-content-section"}>
                <div className={"permissions user"}>
                    { users && <UserList users={ users.users }/> }
                </div>
            </Content>
        </Layout>
    );
};

export default Permissions;
