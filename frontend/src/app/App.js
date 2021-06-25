import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import AppRouter from './AppRouter';

import Navbar from './components/Navbar';

import Footer from './components/Footer';
import './App.scss';

import GraphList from './components/GraphList';




class App extends Component {
    state = {}

    componentDidMount() {
        this.onRouteChanged();
    }

    render() {

        let graph_list_component = !this.state.isFullPageLayout ? <GraphList/> : '';


        return (
            <div className="container-scroller">


                <div className="container-fluid page-body-wrapper">
                   
                    <div className="main-panel">
                        <div className="content-wrapper" >
                            <AppRouter/>
                            {graph_list_component}

                        </div>
                        {/*{footerComponent}*/}
                    </div>
                </div>
            </div>
        );
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location) {
            this.onRouteChanged();
        }
    }

    onRouteChanged() {
        // console.log("ROUTE CHANGED");

        window.scrollTo(0, 0);
        const fullPageLayoutRoutes = ['/pages/Login', '/pages/Register', '/functions/ValidateLogin', '/functions/ValidateRegister'];
        for ( let i = 0; i < fullPageLayoutRoutes.length; i++ ) {
            if (this.props.location.pathname === fullPageLayoutRoutes[i]) {
                this.setState({
                    isFullPageLayout: true
                })
                document.querySelector('.page-body-wrapper').classList.add('full-page-wrapper');
                break;
            } else {
                this.setState({
                    isFullPageLayout: false
                })
                document.querySelector('.page-body-wrapper').classList.remove('full-page-wrapper');
            }
        }
    }



}


export default withRouter(App);
