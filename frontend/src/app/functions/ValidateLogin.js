import React, { Component } from 'react';
import {Link} from "react-router-dom";


class ValidateLogin extends Component{

    constructor() {
        super();
        this.state = {
            url: ""
        }
    }

    componentDidMount (){
        console.log(this.props)
        console.log("UPPER userName :  " + this.props.userName)
        console.log("UPPER userPass :  " + this.props.userPassword)

        if(this.props.userName.toUpperCase() == "ADMIN" && this.props.userPassword.toUpperCase() == "ADMIN" ){
            this.state.url = "/dashboard";
        }else{
            this.state.url = "/pages/Login"
        }
    }

    render() {
        return(
            <div className="ValidateLogin">
                <Link to={this.state.url}>
                    <button type="submit">CHECK</button>
                </Link>
            </div>
        )
    }
}



export default ValidateLogin