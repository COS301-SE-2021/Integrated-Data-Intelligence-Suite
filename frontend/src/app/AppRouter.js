import React, { Component,Suspense, lazy } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';

import Spinner from '../app/components/Spinner';

const Dashboard = lazy(() => import('./dashboard/Dashboard'));

/*const Buttons = lazy(() => import('./basic-ui/Buttons'));
const Dropdowns = lazy(() => import('./basic-ui/Dropdowns'));

const BasicElements = lazy(() => import('./form-elements/BasicElements'));

const BasicTable = lazy(() => import('./tables/BasicTable'));

const Mdi = lazy(() => import('./icons/Mdi'));

const ChartJs = lazy(() => import('./charts/ChartJs'));

const Error404 = lazy(() => import('./error-pages/Error404'));
const Error500 = lazy(() => import('./error-pages/Error500'));*/

const Login = lazy(() => import('./pages/Login'));
const Register1 = lazy(() => import('./pages/Register'));


class AppRoutes extends Component {
    render () {
        return (
            <Suspense fallback={<Spinner/>}>
                <Switch>
                    <Route exact path="/dashboard" component={ Dashboard } />

                    {/*
                    <Route path="/basic-ui/buttons" component={ Buttons } />
                    <Route path="/basic-ui/dropdowns" component={ Dropdowns } />

                    <Route path="/form-Elements/basic-elements" component={ BasicElements } />

                    <Route path="/tables/basic-table" component={ BasicTable } />

                    <Route path="/icons/mdi" component={ Mdi } />

                    <Route path="/charts/chart-js" component={ ChartJs } />

                    <Route path="/error-pages/error-404" component={ Error404 } />
                    <Route path="/error-pages/error-500" component={ Error500 } />
                    */}


                    <Route path="/pages/Login" component={ Login } />
                    <Route path="/pages/Register" component={ Register1 } />




                    <Redirect to="/dashboard" />
                </Switch>
            </Suspense>
        );
    }
}

export default AppRoutes;