import React, { Component } from 'react';
import { Dropdown } from 'react-bootstrap';

class Navbar extends Component {
 
    render () {
        return (
            <nav className="w3-bar w3-top w3-black w3-large" /*style={{zIndex :'4'}}*/>
                <button className="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey">
                    <i className="fa fa-bars"></i> Menu
                </button>
                <span className="w3-bar-item w3-left">Data Suite</span>
            </nav>
        );
    }


}

export default Navbar;