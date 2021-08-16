import {Redirect} from "react-router-dom";
import React from "react";


const LogoutPage = () =>{

    localStorage.clear()

    return <Redirect to={'/'}/>
}

export default LogoutPage;