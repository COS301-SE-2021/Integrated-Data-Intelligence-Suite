import './App.scss';
import React, {Component} from 'react';
import {
    Input, Layout
} from 'antd';
import {Typography} from 'antd';
import Login from './pages/Login';
import {Route, Switch} from "react-router-dom";
import Register from "./pages/Register";
import HomePage from "./pages/HomePage";

const {Title, Text} = Typography;
const {Header, Footer, Sider, Content} = Layout;


class App extends Component {
    state = {}

    render() {
        return (
            <>
                <Switch>
                    <Route exact path='/'>
                        <HomePage/>
                    </Route>

                    <Route exact path='/login'>
                        <Login/>
                    </Route>
                </Switch>

            </>
        );
    }

}

export default App;
