import './App.scss';
import React, {Component} from 'react';
import {Link, Route, Switch} from "react-router-dom";
import LoginPage from './pages/LoginPage/LoginPage';
import HomePage from "./pages/HomePage/HomePage";
import ChartPage from "./pages/ChartPage/components/ChartPage";
import RegisterPage from "./pages/RegisterPage/RegisterPage";
import UserPermissions from "./pages/UserPermissionsPage/UserPermissions";
import Permissions from "./pages/PermissionsPage/Permissions";

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

                    <Route exact path='/register'>
                        <RegisterPage/>
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

                    <Route path="*">
                        <div>
                            <h2>Page not Fount</h2>
                            <Link to="/"> back to home page</Link>
                        </div>
                    </Route>

                </Switch>
            </>
        );
    }

}

export default App;
