import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Collapse } from 'react-bootstrap';
import { Dropdown } from 'react-bootstrap';

class Sidebar extends Component {
    /*state = {};

    toggleMenuState(menuState) {
        if (this.state[menuState]) {
            this.setState({[menuState] : false});
        } else if(Object.keys(this.state).length === 0) {
            this.setState({[menuState] : true});
        } else {
            Object.keys(this.state).forEach(i => {
                this.setState({[i]: false});
            });
            this.setState({[menuState] : true});
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location) {
            this.onRouteChanged();
        }
    }

    onRouteChanged() {
        document.querySelector('#sidebar').classList.remove('active');
        Object.keys(this.state).forEach(i => {
            this.setState({[i]: false});
        });

        const dropdownPaths = [
            {path:'/apps', state: 'appsMenuOpen'},
            {path:'/basic-ui', state: 'basicUiMenuOpen'},
            {path:'/form-elements', state: 'formElementsMenuOpen'},
            {path:'/tables', state: 'tablesMenuOpen'},
            {path:'/icons', state: 'iconsMenuOpen'},
            {path:'/charts', state: 'chartsMenuOpen'},
            {path:'/user-pages', state: 'userPagesMenuOpen'},
            {path:'/error-pages', state: 'errorPagesMenuOpen'},
        ];

        dropdownPaths.forEach((obj => {
            if (this.isPathActive(obj.path)) {
                this.setState({[obj.state] : true})
            }
        }));

    }*/

    render () {
        return (

            <nav className="w3-sidebar w3-collapse w3-white w3-animate-left" style={{zIndex:'3', width:'300px'}} id="mySidebar">
                <br/>
                <div className="w3-container w3-row">
                    {/*<div className="w3-col s4">
                        <img src="/w3images/avatar2.png" alt={"image"} className="w3-circle w3-margin-right" style={{width:'46px'}}/>
                    </div>*/}

                    <div className="w3-col s8 w3-bar">
                        <span>Welcome <strong>User</strong></span><br/>
                    </div>
                </div>

                <hr/>
                <div className="w3-container">
                    <h5>Intelligence Suite</h5>
                </div>
                <div className="w3-bar-block">

                    <Link to="#">
                        <a href="#" className="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black"
                       onClick={this.w3_close} title="close menu">
                        <i className="fa fa-remove fa-fw"></i> Close Menu</a>
                    </Link>

                    <Link to="/dashboard">
                        <a href="../dashboard/Dashboard.js" className="w3-bar-item w3-button w3-padding w3-blue">
                        <i className="fa fa-users fa-fw"></i> Dashboard</a>
                    </Link>

                    <Link to="/pages/Login">
                        <a href="pages/Login" className="w3-bar-item w3-button w3-padding">
                        <i className="fa fa-eye fa-fw"></i> Login</a>
                    </Link>

                    <Link to="/pages/Register">
                    <a href="../pages/Register.js" className="w3-bar-item w3-button w3-padding">
                        <i className="fa fa-users fa-fw"></i> Register</a>
                    </Link>

                </div>
            </nav>
        );
    }

    //slide open/close the side bar with button
}

export default withRouter(Sidebar);