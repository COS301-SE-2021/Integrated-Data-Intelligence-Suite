import React, { Component }from 'react';
import { Link } from 'react-router-dom';
import '../../styles/login.css';

class Register extends  Component{


    constructor() {
        super();
        this.state = {
            userName : "",
            userPassword: "",
            userPasswordRepeat: ""
        }
    }


    componentDidMount () {
        let sbutton = document.getElementById("submitButton");
        //sbutton.disabled = true;
    }

    render() {
        return (
            <div className="Login">
                <form action="" method="post">
                    <div className="container">
                        <label htmlFor="uname"><b>Username</b></label>
                        <input type="text" placeholder="Enter Username" id="uname" required/>

                        <label htmlFor="uname"><b>Nickname</b></label>
                        <input type="text" placeholder="Enter Nickname" id="nname" required/>

                        <label htmlFor="psw"><b>Password</b></label>
                        <input type="password" placeholder="Enter Password" id="upassword" required/>

                        <label htmlFor="psw"><b>Password repeat</b></label>
                        <input type="password" placeholder="Re-Enter Password" id="upasswordR" required/>

                        <Link to={{
                            pathname: "/functions/ValidateRegister",
                            state: this.state
                        }}>
                            <button type="submit"  onClick={() =>{this.checkUserCreds()}} > Register</button>
                        </Link>
                    </div>

                </form>
            </div>
        );
    }

    checkUserCreds(object) {
        //console.log(object)
        console.log(document.getElementById("uname"));
        console.log(document.getElementById("upassword"));


        this.state.userPassword = document.getElementById("upassword").value;
        this.state.userName = document.getElementById("uname").value;
        this.state.userPasswordRepeat = document.getElementById("upasswordR").value;


        console.log("user name : " + this.state.userName);
        console.log("user password : " + this.state.userPassword);
    }

}

export default Register;