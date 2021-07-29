import React, {Component} from 'react';
import {
    Skeleton, Switch, Card, Avatar, Dropdown, Input, Space, Layout, Menu, Breadcrumb, Button
} from 'antd';
import {
    HomeOutlined, BarChartOutlined, SettingOutlined, LockOutlined, PoweroffOutlined, EllipsisOutlined
} from '@ant-design/icons';
import {Route} from 'react-router-dom';
import TopNavBar from "./components/TopNavBar";
import './App.scss';
import GraphList from './components/GraphList';
import Login from './pages/Login';

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

class SiderDemo extends React.Component {
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
                <div id="logo"></div>
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
                <UserInfoCard></UserInfoCard>

            </Sider>
        );
    }
}

class UserInfoCard extends React.Component {
    state = {
        loading: true,
    };

    onChange = checked => {
        this.setState({loading: !checked});
    };

    render() {
        const {loading} = this.state;

        return (
            <>
                {/*<Switch checked={!loading} onChange={this.onChange} style={{width: 40}}/>*/}
                <Card id={'user_avatar_card'}>
                    <Skeleton loading={false} avatar active>
                        <Meta
                            id={'meta_id'}
                            className={'user_meta_card'}
                            title="Myron Lopes"
                            description="youremail@host.com"

                            //Image/profile Picture
                            avatar={
                                <Avatar id={'user_avatar_pic'}
                                        // size={28}
                                        shape={'square'}>
                                    M
                                </Avatar>
                            }
                        />
                        <Dropdown
                            overlay={exit_menu}
                            placement="topCenter"
                            arrow={true}
                            trigger={'click'}
                        >
                            <Button id={'exit_menu_button'}
                                    icon={
                                        <EllipsisOutlined
                                            className={'exit_menu_ellipsis_icon'}
                                            style={{fontSize: '25px'}}
                                        />
                                    }
                            >
                            </Button>
                        </Dropdown>
                    </Skeleton>
                </Card>

            </>
        );
    }
}

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
                    <SiderDemo></SiderDemo>
                    <Layout>
                        <Header id={'top_bar'}>
                            {/*<Avatar id={'user_avatar'}>M</Avatar>*/}
                            <Search id={'search_input'}
                                    placeholder="input search text"
                                    allowClear
                                    style={{width: 200}}/>
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
