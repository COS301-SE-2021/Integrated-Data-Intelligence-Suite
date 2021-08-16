import React, {Component} from 'react';
import SideBar from "../../components/SideBar/SideBar";
import {
    Input, Layout
} from 'antd';
import {Typography} from 'antd';
import {Redirect, Route, Switch} from "react-router-dom";
import UserInfoCard from "../../components/SideBar/UserInfoCard";

const {Title, Text} = Typography;
const {Header, Footer, Sider, Content} = Layout;

function getLocalUser(){
    const localUser = localStorage.getItem("user");
    if(localUser){
        // console.log("user logged in is ", localUser)
        return JSON.parse(localUser);
    }else{
        return null;
    }
}

class HomePage extends Component {
    constructor(props) {
        super(props);
        this.state.user = getLocalUser();
    }
    state = {
        user : null
    }

    render() {
        if(this.state.user){
            return (
                <>
                    <Switch>
                        <Route exact path='/'>
                            <Layout id={'outer_layout'}>
                                <SideBar/>
                                <Layout>
                                    <Header id={'top_bar'}>
                                        {/*<SearchBar/>*/}
                                        <Title level={1}>Home</Title>
                                    </Header>
                                    <Content id={'content_section'}>Content</Content>
                                    <Footer id={'footer_section'}>Footer</Footer>
                                </Layout>
                            </Layout>
                        </Route>
                    </Switch>
                </>
            );
        }else{
           return <Redirect to={'/login'}/>
        }

    }

}

export default HomePage;
