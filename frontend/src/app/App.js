import './App.scss';
import React, {Component} from 'react';
import SideBar from "./components/SideBar/SideBar";
import {
    Input, Layout
} from 'antd';
import {Typography} from 'antd';
import GraphList from './components/ContentSection/GraphList';
import Login from './pages/Login';
import SearchBar from "./components/Header/SearchBar";
import {Route, Switch} from "react-router-dom";
import Register from "./pages/Register";

const {Title, Text} = Typography;
const {Header, Footer, Sider, Content} = Layout;


class App extends Component {
    state = {}


    // componentDidMount() {
    //     this.onRouteChanged();
    // }

    render() {
        // let graph_list_component = !this.state.isFullPageLayout ? <GraphList/> : '';
        return (
            <>
                <Layout id={'outer_layout'}>
                    <SideBar/>
                    <Layout>
                        <Header id={'top_bar'}>
                            {/*<Avatar id={'user_avatar'}>M</Avatar>*/}
                            <SearchBar/>
                            <Title level={1}>Home</Title>
                            <Title level={3} italic>Summary of Changes</Title>
                            <Text level={5} italic>last updated: July 30, 06:00</Text>
                        </Header>
                        <Content id={'content_section'}>Content</Content>
                        <Footer id={'footer_section'}>Footer</Footer>
                    </Layout>
                </Layout>



                <Switch>
                    <Route exact path='/' component={Login}/>
                    <Route exact path='/login' component={GraphList}/>
                </Switch>

            </>
        );
    }

}

export default App;
