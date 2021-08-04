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

    const {data:user, isPending, error} = useGet('http://localhost:8000/people/'+ id)


    const enableSubmit = (value) => {
        setPermission(value);
        setSubmit(true);
    }

    const submitChanges = (e) =>{
        e.preventDefault();
        setSubmit(false);
        user.permissions = permission;
        fetch('http://localhost:8000/people/'+id,{
            method:"PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        }).then( () => {
            console.log("uploaded")
            history.go(-1);
        })

    }



    return (

        <Layout id={'outer_layout'}>
            <SideBar/>
            <Layout>
                <Header id={'top_bar'}>
                    {/*<SearchBar/>*/}
                    <Title level={1}>Permission Management</Title>
                </Header>
                <Content id={'content_section'}>
                    <div className={"permissions"}>
                        <div>
                            { isPending && <div>loading </div>}
                            { error && <div>{error}</div>}
                            {user && permission===null && setPermission(user.permission)}
                            {user && (
                                <div>
                                    <h3>{user.firstName}</h3>
                                    <p>{user.username}</p>
                                    <p>current permission : {user.permission}</p>
                                    <form onSubmit={submitChanges}>
                                        <select
                                            value={permission}
                                            onChange={(e) => enableSubmit(e.target.value)}
                                        >
                                            <option value={"VIEWING"}>VIEWING</option>
                                            <option value={"IMPORTING"}>IMPORTING</option>
                                        </select>
                                        <br/>
                                        {!submit && <button disabled>submit</button>}
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
