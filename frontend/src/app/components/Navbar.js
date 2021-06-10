import React, { Component } from 'react';
import { Dropdown } from 'react-bootstrap';

class Navbar extends Component {
    /*toggleOffcanvas() {
        document.querySelector('.sidebar-offcanvas').classList.toggle('active');
    }
    toggleRightSidebar() {
        document.querySelector('.right-sidebar').classList.toggle('open');
    }*/
    render () {
        return (
            <nav className="w3-bar w3-top w3-black w3-large" style={{zIndex :'4'}}>
                <button className="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey">
                    <i className="fa fa-bars"></i> Menu
                </button>
                <span className="w3-bar-item w3-right">LOGO</span>
            </nav>
        );
    }


}

export default Navbar;