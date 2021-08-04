import UserList from "../../components/ContentSection/UserList";
import SideBar from "../../components/SideBar/SideBar";
import {Layout} from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import Title from "antd/es/typography/Title";
import useGet from "../../functions/useGet";


const Permissions = () => {


    const { data:users, isPending, error } = useGet('http://localhost:8000/people');

    return (

    <Layout id={'outer_layout'}>
        <SideBar/>
        <Layout>
            <Header id={'top_bar'}>
                {/*<SearchBar/>*/}
                <Title level={1}>Permission Management</Title>
            </Header>
            <Content id={'content_section'}>
                <div className={"permissions"}>
                    { error && <div>{ error }</div> }
                    { isPending && <div>Loading...</div> }
                    {/*{ users && <UserList users={ users }/> }*/}
                </div>
            </Content>
            <Footer id={'footer_section'}>Footer</Footer>
        </Layout>
    </Layout>
    );
};

export default Permissions;
