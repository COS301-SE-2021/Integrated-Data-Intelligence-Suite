import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import AppRouter from './AppRouter';

import Navbar from './components/Navbar';

import Footer from './components/Footer';
import './App.scss';

import GraphList from './components/GraphList';


/*function App() {
  /*return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );

    return (
        <Router>
            <div className="App">
                <nav className="navbar navbar-expand-lg navbar-light fixed-top">
                    <div className="container">
                        <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
                            <ul className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link className="nav-link" to={"/Login"}>Sign in</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to={"/Register"}>Sign up</Link>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>

                <div className="outer">
                    <div className="inner">
                        <Switch>
                            <Route exact path='/' component={Login} />
                            <Route path="/Login" component={Login} />
                            <Route path="/Register" component={Register} />
                        </Switch>
                    </div>
                </div>
            </div>
        </Router>
    );

    /*render()
    {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <p>
                        Edit <code>src/App.js</code> and save to reload.
                    </p>
                    <a
                        className="App-link"
                        href="<Login />"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Learn React
                    </a>
                </header>
            </div>
        );
    //}

    /*return (
        ReactDOM.render(
            <div className="App" id="App">
                <form action="components/Login.js" method="get">
                    <header className="App-header">
                        <img src={logo} className="App-logo" alt="logo"/>
                        <p>
                            Edit <code>src/App.js</code> and save to reload.
                        </p>

                        <input type='submit' value='Log' />

                    </header>
                </form>


            </div>,
            document.getElementById('root')
        )
    )

}*/


class App extends Component {
    state = {}

    componentDidMount() {
        this.onRouteChanged();
    }

    render() {
        // let navbarComponent = !this.state.isFullPageLayout ? <Navbar/> : '';
        // let footerComponent = !this.state.isFullPageLayout ? <Footer/> : '';
        let graph_list_component = !this.state.isFullPageLayout ? <GraphList/> : '';


        return (
            <div className="container-scroller">
                {/*{navbarComponent}*/}
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
        console.log("ROUTE CHANGED");
        /*const { i18n } = this.props;
        const body = document.querySelector('body');
        if(this.props.location.pathname === '/layout/RtlLayout') {
            body.classList.add('rtl');
            i18n.changeLanguage('ar');
        }
        else {
            body.classList.remove('rtl')
            i18n.changeLanguage('en');
        }*/
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
