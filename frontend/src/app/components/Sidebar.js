import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';


class Sidebar extends Component {


    render () {
        return (

            <nav className="w3-sidebar w3-collapse w3-white w3-animate-left" style={{zIndex:'3', width:'300px'}} id="mySidebar">
                <br/>
                <div className="w3-container w3-row">

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
                        title="close menu">
                        <i className="fa fa-remove fa-fw"/> Close Menu</a>
                    </Link>

                    <Link to="/dashboard">
                        <a href="../dashboard/Dashboard.js" className="w3-bar-item w3-button w3-padding w3-blue">
                        <i className="fa fa-users fa-fw"/> Dashboard</a>
                    </Link>

                    <Link to="/pages/Login">
                        <a href="../pages/Login.js" className="w3-bar-item w3-button w3-padding">
                        <i className="fa fa-eye fa-fw"/> Login</a>
                    </Link>

                    <Link to="/pages/Register">
                    <a href="../pages/Register.js" className="w3-bar-item w3-button w3-padding">
                        <i className="fa fa-users fa-fw"/> Register</a>
                    </Link>

                </div>
            </nav>
        );
    }

    //slide open/close the side bar with button
}

export default withRouter(Sidebar);