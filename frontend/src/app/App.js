import React, {Component} from 'react';
import {Route, Switch} from 'react-router-dom';
import TopNavBar from "./components/TopNavBar";

import './App.scss';

import GraphList from './components/GraphList';
import Login from './pages/Login';


class App extends Component {
    state = {}

    // componentDidMount() {
    //     this.onRouteChanged();
    // }

    render() {
        let graph_list_component = !this.state.isFullPageLayout ? <GraphList/> : '';

        return (
            <>
                <TopNavBar/>
                <Switch>
                    <Route path='/login' component={Login}/>
                    <Route path='/' component={GraphList}/>
                </Switch>
            </>
        );
    }

}

export default App;
