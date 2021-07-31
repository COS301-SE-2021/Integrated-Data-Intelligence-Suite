import React, { Component,Suspense, lazy } from 'react';
import { Switch, Route, Redirect, Link, useLocation } from 'react-router-dom';

import Spinner from './components/ContentSection/Spinner';


// const Dashboard = lazy(() => import('./dashboard/Dashboard'));



const Login = lazy(() => import('./pages/Login'));
const Register1 = lazy(() => import('./pages/Register'));

const ValidateLogin = lazy(() => import('./functions/ValidateLogin'));
const ValidateRegister = lazy(() => import('./functions/ValidateRegister'));


class AppRoutes extends Component {
    render () {
        return (
            <Suspense fallback={<Spinner/>}>
                <Switch>
                    {/*<Route exact path="/dashboard" component={ Dashboard } />*/}

                    <Route path="/pages/Login" component={ Login } />
                    <Route path="/pages/Register" component={ Register1 } />

                    <Route path="/functions/ValidateLogin" component={ VLogin} />}
                    <Route path="/functions/ValidateRegister" component={ VRegister} />}


                    {/*<Redirect to="/dashboard" />*/}
                </Switch>
            </Suspense>
        );
    }
}

function VLogin (){
    //let userName,userPassword;
    const location = useLocation();

    let userName = location.state.userName;
    let userPassword = location.state.userPassword;

    console.log("Checking U-N : " + userName);
    console.log("Checking U-P : " + userPassword);

    /*React.useEffect(() => {

        //console.log(this)
        //console.log(this.props.location.state.userName)

        //const { handle } = this.props.match.params;

        userName = location.state.userName;
        userPassword = location.state.userPassword;

        //console.log("Checking HHHHH: " +  handle);
        //const { fromNotifications } = this.props.location.state;
       /*fetch(`https://api.twitter.com/user/${handle}`)
            .then((user) => {
                this.setState(() => ({ user }))
            })*
        console.log("Checking U-N : " + userName);
        console.log("Checking U-P : " + userPassword);
    }, [])*/


    return (
        <ValidateLogin userName={userName} userPassword ={userPassword} />
    )
}

function VRegister (){
    //let userName,userPassword;
    const location = useLocation();

    let userName = location.state.userName;
    let userPassword = location.state.userPassword;
    let userPasswordRepeat = location.state.userPasswordRepeat;

    console.log("Checking U-N : " + userName);
    console.log("Checking U-P : " + userPassword);


    return (
        <ValidateRegister userName={userName} userPassword={userPassword} userPasswordRepeat={userPasswordRepeat}/>
    )
}

export default AppRoutes;