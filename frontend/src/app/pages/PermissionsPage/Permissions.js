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
            <div className={"clear-background"} id={"permissions_header"}>
                <h1 id={"permissions-title"}>Permissions</h1>
            </div>
            <Content className={"permissions-content-section"}>
                <div className={"permissions user"}>
                    { error && <div>{ error }</div> }
                    { isPending && <div>Loading...</div> }
                    { users && <UserList users={ users.users }/> }
                </div>
            </Content>
        </Layout>
    );
};

export default Permissions;
