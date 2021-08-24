import UserList from "../../components/UserList/UserList";
import SideBar from "../../components/SideBar/SideBar";
import {Divider, Layout, Typography} from "antd";
import useGet from "../../functions/useGet";
import React, {Component, useRef} from 'react';

const {Content, Footer, Header} = Layout;
const {Title} = Typography;


const Permissions = () => {
    const {data: users, isPending, error} = useGet('/user/getAll');

    return (
        <Layout>
            <Content className={"permissions-content-section"}>
                <div className={"permissions user"}>
                    {users && <UserList users={users.users}/>}
                </div>
            </Content>
        </Layout>
    );
};

export default Permissions;
