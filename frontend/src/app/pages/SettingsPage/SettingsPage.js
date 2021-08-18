import React, {useState} from 'react';
import {Layout, Row, Col, Divider} from 'antd';
import {CloseCircleTwoTone} from '@ant-design/icons'
import { useHistory} from "react-router-dom";
import Permissions from "../PermissionsPage/Permissions";
import ProfilePage from "../ProfilePage/ProfilePage";


const { Content} = Layout;

const setActive = (component) =>{
    let options = document.getElementsByClassName("option");
    console.log(options)
    for (let i = 0; i < options.length; i++) {
        console.log(options[i].id)
        if(options[i].id === component){
            options[i].className = "option active"
        }else{
            options[i].className = "option"
        }
    }
    return true;
}

function getLocalUser() {
    const localUser = localStorage.getItem("user");
    if (localUser) {
        // console.log("user logged in is ", localUser)
        return JSON.parse(localUser);
    } else {
        return null;
    }
}




const SettingsPage = () => {

    const [component, setComponent] = useState("profile");
    const [user, setUser] = useState(getLocalUser());
    const history = useHistory();


    return (
        <Layout className={"bodyDiv"}>
            <div className={"header white-background"}>
            </div>
                <Content id={"settings-container"} className={"outer-container"} style={{ margin: '0' , minHeight:"100vh"}}>
                    <Row className={"row"}>
                        <Col style={{padding:"30px 20px"}} className={"left-column"} flex="200px">
                            <div id={"Profile"} className={"active option"} onClick={()=>setComponent("Profile")}>Profile</div>
                            <Divider />

                            { user && user.isAdmin && <div id={"Permissions"} className={"option"} onClick={()=>setComponent("Permissions")}>Permissions</div>}
                            {user && user.isAdmin && <Divider />}

                            <div id={"Account"} className={"option"} onClick={()=>setComponent("Account")}>Account</div>
                        </Col>
                        <Col style={{padding:"0 0px 30px 0", backgroundColor:"#eff0f0"}} className={"right-column"} flex="auto">
                            {<div className={"top-padding"}><CloseCircleTwoTone className={"back-button"} onClick={()=>history.go(-1)}/></div>}
                            { component === "Permissions" && user && user.isAdmin && setActive(component) && <Permissions/>}
                            { component === "Profile" && setActive(component) && <div> <h1>Page not implemented</h1></div>}
                            { component === "Account" && setActive(component) && <div><h1>Page not implemented</h1></div>}
                        </Col>
                    </Row>
                </Content>
        </Layout>

    );
};
export default SettingsPage;
