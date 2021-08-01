import React, {Component} from 'react';
import {Link} from "react-router-dom";

class Login extends Component {
    render() {
        return (
            <>
                <div style={{
                    width: 200,
                    height: 200,
                    backgroundColor: "red",
                }}>
                    This is the Login Page
                </div>

                <span
                    style={{
                        width: 50,
                        height: 50,
                        backgroundColor: 'yellow'
                    }}
                >
                    <Link to="/">Go back home</Link>
                </span>
            </>
        );
    }
}

export default Login;