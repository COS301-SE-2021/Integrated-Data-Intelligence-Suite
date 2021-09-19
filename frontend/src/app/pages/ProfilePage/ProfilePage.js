import React, { useState } from 'react';
import './ProfilePage.css';
import { Checkbox, Divider, Input } from 'antd';
import useGet from '../../functions/useGet';

function getLocalUser() {
    const localUser = localStorage.getItem('user');
    if (localUser) {
        console.log('user logged in is ', localUser);
        return JSON.parse(localUser);
    }
    return null;
}

const getUser = function () {
    const localUser = getLocalUser();
    if (localUser && localUser.id === 'b5aa283d-35d1-421d-a8c6-42dd3e115463') {
        return [{ data: localUser, isPending: false, error: false }];
    }
    return useGet(`/user/getUser/${localUser.id}`);

};

export default function ProfilePage(props) {
    const [username, setUsername] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [isAdmin, setIsAdmin] = useState(true);
    const [ready, setReady] = useState(false);
    let { data, isPending, error } = getUser();
    // console.log(userData.data);

    const setFields = function () {
        if (data.success) {
            if (data.user[0]) {
               const usr = data.user[0];
               if (usr.firstName) {
                   setFirstName(usr.firstName);
               }
               if (usr.lastName) {
                   setLastName(usr.lastName);
               }
               if (usr.email) {
                   setEmail(usr.email);
               }
               if (usr.username) {
                   setUsername(usr.username);
               }
            } else {
                error = 'Could not load user information.';
                data = null;
            }
        } else {
            error = data.message;
            data = null;
        }
        setReady(true);
        return true;
    };

    return (
        <>
            <div id="profile-page-container">
                {isPending && (<div>fetching user information</div>)}
                {error && (<div>could not load user data</div>)}
                {data && !ready && setFields() && setReady(true)}
                {data && ready && (
                <form className="profile form">
                    <div className="heading"> General</div>
                    <Divider />
                    <label htmlFor="usernameInput">Username</label>
                    <Input
                      size="default"
                      placeholder="username"
                      name="usernameInput"
                      type="text"
                      value={username}
                      onChange={(event) => setUsername(event.target.value)}
                    />
                    <label htmlFor="firstNameInput">First Name</label>
                    <Input
                      size="default"
                      placeholder="First Name"
                      name="firstNameInput"
                      type="text"
                      value={firstName}
                      onChange={(event) => setFirstName(event.target.value)}
                    />
                    <label htmlFor="lastNameInput">Last Name</label>
                    <Input
                      size="default"
                      placeholder="Last Name"
                      name="lastNameInput"
                      type="text"
                      value={lastName}
                      onChange={(event) => setLastName(event.target.value)}
                    />
                    <label htmlFor="emailInput">Email</label>
                    <Input
                      size="default"
                      placeholder="Email"
                      name="emailInput"
                      type="text"
                      value={email}
                      onChange={(event) => setEmail(event.target.value)}
                    />
                    <div className="heading second">Administration</div>
                    <Divider />
                    <div className="switch_box box_1">
                        <input name="admin-switch" type="checkbox" checked={isAdmin} disabled className="switch_1" />
                        <label id="admin-label" className="field-name" htmlFor="Admin-switch">Administrator</label>
                    </div>
                    <div>
                        <button type="submit" className="enabled">submit</button>
                    </div>
                </form>
)}
            </div>
        </>
    );
}
