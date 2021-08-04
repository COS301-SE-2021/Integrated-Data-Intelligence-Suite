import './App.scss';
import React, {Component} from 'react';
import {Route, Switch} from "react-router-dom";
import LoginPage from './pages/LoginPage/LoginPage';
import HomePage from "./pages/HomePage/HomePage";
import ChartPage from "./pages/ChartPage/ChartPage";
import Permissions from "./pages/PermissionsPage/Permissions";
import UserPermissions from "./pages/UserPermissionsPage/UserPermissions";




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

                    <Route exact path='/chart'>
                        <ChartPage/>
                    </Route>

                    <Route exact path="/permissions">
                        <Permissions/>
                    </Route>

                    <Route path={"/permissions/user/:id"}>
                        <UserPermissions/>
                    </Route>


                </Switch>
            </>
        );
    }

}

export default App;
