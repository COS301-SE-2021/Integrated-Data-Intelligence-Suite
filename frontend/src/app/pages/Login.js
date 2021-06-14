import React, { Component }from 'react';
import { Link } from 'react-router-dom';
import { Form } from 'react-bootstrap';

import ValidateLogin from "../functions/ValidateLogin";

class Login extends  Component{

    constructor() {
        super();
        this.state = {
            userName : "",
            userPassword: ""
        }
    }


    componentDidMount () {
        let sbutton = document.getElementById("submitButton");
        console.log("AAAAAAAAAAAAAA" + document.getElementById("uname"))
        //sbutton.disabled = true;
    }

    render() {
        return (
            <div className="Login">
                <form action="" method="post">
                    <div className="container">
                        <label htmlFor="uname"><b>Username</b></label>
                        <input type="text" placeholder="Enter Username" id="uname" required/>

                        <label htmlFor="psw"><b>Password</b></label>
                        <input type="password" placeholder="Enter Password" id="upassword" required/>

                        <Link to={{
                            pathname: "/functions/ValidateLogin",
                            state: this.state
                        }}>
                            <button type="submit" id={"submitButton"} onClick={() =>{this.checkUser()}}>Login</button>
                        </Link>
                    </div>

                </form>
            </div>
        );
    }


    checkUser(object) {
        //console.log(object)

            console.log(document.getElementById("uname"))
            console.log(document.getElementById("upassword"))
            this.state.userPassword = document.getElementById("upassword").value;
            this.state.userName = document.getElementById("uname").value;




        console.log("user name : " + this.state.userName)
        console.log("user password : " + this.state.userPassword)
    }


}






export default Login;