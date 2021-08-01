import './App.scss';
import React, {Component} from 'react';
import {Route, Switch} from "react-router-dom";
import LoginPage from './pages/LoginPage';
import HomePage from "./pages/HomePage";

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
                        <LoginPage/>
                    </Route>
                </Switch>
            </>
        );
    }

}

export default App;
