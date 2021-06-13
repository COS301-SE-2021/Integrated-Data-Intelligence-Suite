import React, { Component }from 'react';
import { Link } from 'react-router-dom';
import { Form } from 'react-bootstrap';

class Login extends  Component{

    render() {
        return (
            <div className="Login">
                <form action="" method="post">
                    <div className="container">
                        <label htmlFor="uname"><b>Username</b></label>
                        <input type="text" placeholder="Enter Username" id="uname" required/>

                        <label htmlFor="psw"><b>Password</b></label>
                        <input type="password" placeholder="Enter Password" id="upassword" required/>

                        <Link to="/dashboard">
                            <button type="submit" onClick={this.checkUser}>Login</button>
                        </Link>
                    </div>

                </form>
            </div>
        );
    }

    checkUser() {
        let uName = document.getElementById("uname")
        let uPassword= document.querySelector('#upassword');

        console.log(uName.value)
    }
}






export default Login;