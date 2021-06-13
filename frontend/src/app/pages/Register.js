import React, { Component }from 'react';
import '../../styles/Login.css';

class Register extends  Component{

    render() {
        return (
            <div className="Login">
                <form action="" method="post">
                    <div className="container">
                        <label htmlFor="uname"><b>Username</b></label>
                        <input type="text" placeholder="Enter Username" name="uname" required/>

                        <label htmlFor="uname"><b>Nickname</b></label>
                        <input type="text" placeholder="Enter Nickname" name="uname" required/>

                        <label htmlFor="psw"><b>Password</b></label>
                        <input type="password" placeholder="Enter Password" name="psw" required/>

                        <label htmlFor="psw"><b>Password repeat</b></label>
                        <input type="password" placeholder="Re-Enter Password" name="psw" required/>

                        <button type="submit">Register</button>
                    </div>

                </form>
            </div>
        );
    }
}

export default Register;