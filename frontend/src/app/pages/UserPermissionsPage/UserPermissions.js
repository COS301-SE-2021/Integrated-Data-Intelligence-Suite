import React, {useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import useGet from "../../functions/useGet";
import {Layout} from "antd";
import SideBar from "../../components/SideBar/SideBar";
import {Content, Footer, Header} from "antd/es/layout/layout";
import Title from "antd/es/typography/Title";
import UserList from "../../components/ContentSection/UserList";

const Checkbox = ({ label, value, onChange }) => {
    return (
        <label>
            <input type="checkbox" checked={value} onChange={onChange} />
            {label}
        </label>
    );
};

const UserPermissions = () => {

    const { id } = useParams();
    const history = useHistory();
    const [permission, setPermission] = useState(null);
    const [submit, setSubmit] = useState(false);
    const [user, setUser] = useState(null);

    const {data:users, isPending, error} = useGet('/user/getUser/'+ id)

    const enableSubmit = (value) => {
        setPermission(value);
        setSubmit(true);
    }

    const submitChanges = (e) =>{
        e.preventDefault();
        setSubmit(false);
        const requestBody = {
            username : user.username,
            newPermission : permission
        }
        console.log("userdata ",user.user);
        console.log("body is ", requestBody)
        fetch('http://localhost:9000/changePermission',{
            method:"POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(requestBody)
        }).then( () => {
            console.log("uploaded")
            history.go(-1);
        })

    }



    return (

        <Layout className={"bodyDiv"}>
            <SideBar/>
            <Layout>
                <Header id={"top_bar"} className={"header"} >
                    <Title level={1}>Settings</Title>
                </Header>
                <Content className={"permissions-content-section"}>
                    <div className={"permissions"}>
                        <div className={"user-info"}>
                            { isPending && <div>loading </div>}
                            { error && <div>{error}</div>}
                            {users && user===null && setUser(users.user[0])}
                            {user && permission===null && setPermission(user.permission)}
                            {user && (
                                <div>
                                    <h2>{user.firstName}</h2>

                                    <form onSubmit={submitChanges}>
                                        <label>Username</label><br/>
                                        {<input type={"text"} value={user.username}/>}
                                        <label>Permission</label><br/>
                                        <select
                                            value={permission}
                                            onChange={(e) => enableSubmit(e.target.value)}
                                        >
                                            <option value={"VIEWING"}>VIEWING</option>
                                            <option value={"IMPORTING"}>IMPORTING</option>
                                        </select>
                                        <br/>

                                            {!submit && <button disabled  className={"disabled"}>submit</button>}
                                            {submit && <button>submit</button>}

                                    </form>
                                </div>
                            )}
                        </div>
                    </div>
                </Content>
                <Footer id={'footer_section'}>Footer</Footer>
            </Layout>
        </Layout>
    );
};

export default UserPermissions;
