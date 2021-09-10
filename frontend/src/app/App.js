import React, { Component } from 'react';
import { Link, Route, Switch } from 'react-router-dom';
import LoginPage from './pages/LoginPage/LoginPage';

import HomePage from './pages/HomePage/HomePage';
import ChartPage from './pages/ChartPage/ChartPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import UserPermissions from './pages/UserPermissionsPage/UserPermissions';
import Permissions from './pages/PermissionsPage/Permissions';
import LogoutPage from './pages/LogoutPage/LogoutPage';
import SettingsPage from './pages/SettingsPage/SettingsPage';
import AddDataSource from './pages/AddDataSourcePage/AddDataSource';
import VerifyPage from './pages/VerifyPage/VerifyPage';
import './App.scss';

class App extends Component {
    render() {
        return (
            <>
                <Switch>
                    <Route exact path="/">
                        <HomePage/>
                    </Route>

                    <Route exact path="/login">
                        <LoginPage/>
                    </Route>

                    <Route exact path="/register">
                        <RegisterPage/>
                    </Route>

                    <Route exact path="/chart">
                        <ChartPage/>
                    </Route>

                    <Route exact path="/verify">
                        <VerifyPage/>
                    </Route>

                    <Route exact path="/permissions">
                        <Permissions/>
                    </Route>


                    <Route exact path="/logout">
                        <LogoutPage/>
                    </Route>

                    <Route exact path="/settings">
                        <SettingsPage/>
                    </Route>

                    <Route path="/settings/source/:id">
                        <AddDataSource/>
                    </Route>

                    <Route path="/user/:id">
                        <UserPermissions/>
                    </Route>

                    <Route path="*">
                        <div>
                            <h2>Page not Found</h2>
                            <Link to="/"> back to home page</Link>
                        </div>
                    </Route>
                </Switch>
            </>
        );
    }
}

export default App;
