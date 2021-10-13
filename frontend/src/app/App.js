import React from 'react';
import {
    HashRouter as Router, Link, Redirect, Route, Switch,
} from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import LoginPage from './pages/LoginPage/LoginPage';
import ChartPage from './pages/ChartPage/ChartPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import UserPermissions from './pages/UserPermissionsPage/UserPermissions';
import Permissions from './pages/PermissionsPage/Permissions';
import LogoutPage from './pages/LogoutPage/LogoutPage';
import SettingsPage from './pages/SettingsPage/SettingsPage';
import AddDataSource from './pages/AddDataSourcePage/AddDataSource';
import VerifyPage from './pages/VerifyPage/VerifyPage';
import ResendPage from './pages/ResendPage/ResendPage';
import './App.scss';
import CreditsPage from './pages/CreditsPage/CreditsPage';
import ReportsPage from './pages/ReportsPage/ReportsPage';
import UploadDataPage from './pages/UploadDataPage/UploadDataPage';
import ManageModelsPage from './pages/ManageModelsPage/ManageModelsPage';
import { userState } from './assets/AtomStore/AtomStore';
import SendOTPPage from './pages/SendOTPPage/SendOTPPage';
import ManageSourcesPage from './pages/ManageSourcesPage/ManageSourcesPage';
import ResetPasswordPage from './pages/ResetPasswordPage/ResetPasswordPage';
import NewDesign from './pages/NewDesign/NewDesign';
import TeamPage from './pages/TeamPage/TeamPage';
import ProfilePage from './pages/ProfilePage/ProfilePage';
import ManageUsersPage from './pages/ManageUsersPage/ManageUsersPage';

function App() {
    const user = useRecoilValue(userState);
    return (
        <Router>
            <Switch>
                {/* <Route exact path="/"> */}
                {/*    { */}
                {/*        !user && <Redirect to="/" /> */}
                {/*    } */}
                {/*    { */}
                {/*        user && <Redirect to="/chart" /> */}
                {/*    } */}
                {/* </Route> */}

                <Route exact path="/">
                    <NewDesign />
                </Route>

                <Route exact path="/team">
                    <TeamPage />
                </Route>

                <Route exact path="/login">
                    <LoginPage />
                </Route>

                <Route exact path="/register">
                    <RegisterPage />
                </Route>
                <Route exact path="/chart">
                    <ChartPage />
                </Route>

                <Route exact path="/verify">
                    <VerifyPage />
                </Route>

                <Route exact path="/permissions">
                    <Permissions />
                </Route>

                <Route exact path="/resend">
                    <ResendPage />
                </Route>

                <Route exact path="/logout">
                    <LogoutPage />
                </Route>

                <Route exact path="/settings">
                    <SettingsPage />
                </Route>

                <Route exact path="/profile">
                    <ProfilePage />
                </Route>

                <Route exact path="/manageUsers">
                    <ManageUsersPage />
                </Route>

                <Route path="/settings/source/:id">
                    <AddDataSource />
                </Route>

                <Route path="/user/:id">
                    <UserPermissions />
                </Route>

                <Route exact path="/credits">
                    <CreditsPage />
                </Route>

                <Route exact path="/reports">
                    <ReportsPage />
                </Route>

                <Route exact path="/uploadData">
                    <UploadDataPage />
                </Route>

                <Route exact path="/manageModels">
                    <ManageModelsPage />
                </Route>

                <Route exact path="/manageSources">
                    <ManageSourcesPage />
                </Route>

                <Route exact path="/sendOTP">
                    <SendOTPPage />
                </Route>

                <Route exact path="/resetPassword">
                    <ResetPasswordPage />
                </Route>

                <Route path="*">
                    <div>
                        <h2>Page not Found</h2>
                        <Link to="/"> back to home page</Link>
                    </div>
                </Route>
            </Switch>
        </Router>
    );
}

export default App;
