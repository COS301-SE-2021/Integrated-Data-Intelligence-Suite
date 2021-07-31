import React, {Component} from 'react';
import Sidebar from "./components/Sidebar";
import UserInfoCard from "./components/UserInfoCard";
import {
    Skeleton, Switch, Card, Avatar, Dropdown, Input, Space, Layout, Menu, Breadcrumb, Button
} from 'antd';
import {
    HomeOutlined, BarChartOutlined, SettingOutlined, LockOutlined, PoweroffOutlined, EllipsisOutlined
} from '@ant-design/icons';
import {Typography} from 'antd';
import './App.scss';
import {Route} from 'react-router-dom';
import TopNavBar from "./components/TopNavBar";
import GraphList from './components/GraphList';
import Login from './pages/Login';

const {Title, Text} = Typography;
const {Search} = Input;
const {Header, Footer, Sider, Content} = Layout;
const {Meta} = Card;
const {SubMenu} = Menu;


const exit_menu = (
    <Menu>
        <Menu.Item key="1" icon={<LockOutlined/>}>
            Lock
        </Menu.Item>
        <Menu.Item key="2" icon={<PoweroffOutlined/>}>
            Logout
        </Menu.Item>
    </Menu>
);


class App extends Component {
    state = {}

    changeColorToGreen() {
        // console.log('here');
        // if(document.getElementById('top_bar') != null){
        //
        //     let search_button = document.getElementById('top_bar').childNodes[0].childNodes[1];
        //     search_button.style.borderTop = ' 2px solid #15B761';
        //     search_button.style.borderRight = ' 2px solid #15B761';
        //     search_button.style.borderLeft = ' 2px solid #15B761';
        // }else{
        //     console.log('bug');
        //
        // }
    }


    // componentDidMount() {
    //     this.onRouteChanged();
    // }

    render() {
        // let graph_list_component = !this.state.isFullPageLayout ? <GraphList/> : '';

        return (
            <>
                <Layout id={'outer_layout'}>
                    <Sidebar/>
                    <Layout>
                        <Header id={'top_bar'}>
                            {/*<Avatar id={'user_avatar'}>M</Avatar>*/}
                            <Search id={'search_input'}
                                    placeholder="Looking for something?"
                                    allowClear={false}
                                    onMouseEnter={this.changeColorToGreen()}
                                    onFocus={this.changeColorToGreen()}
                            />

                            <Title level={1}>Hi Wandile</Title>
                            <Title level={3} italic>Summary of Changes</Title>
                            <Text level={5} italic>last updated: July 30, 06:00</Text>


                        </Header>
                        <Content id={'content_section'}>Content</Content>
                        <Footer id={'footer_section'}>Footer</Footer>
                    </Layout>
                </Layout>


                {/*<Switch>*/}
                {/*    <Route path='/login' component={Login}/>*/}
                {/*    <Route path='/' component={GraphList}/>*/}
                {/*</Switch>*/}

            </>
        );
    }

}

export default App;
