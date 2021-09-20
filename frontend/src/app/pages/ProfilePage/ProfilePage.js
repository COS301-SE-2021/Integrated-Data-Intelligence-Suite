import React, { useState } from 'react';
import './ProfilePage.css';
import {
    Button,
    Checkbox, Divider, Input, message,
} from 'antd';
import { useHistory } from 'react-router-dom';
import { Formik, useFormik } from 'formik';
import useGet from '../../functions/useGet';

function getLocalUser() {
    const localUser = localStorage.getItem('user');
    if (localUser) {
        // console.log('user logged in is ', localUser);
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
    const history = useHistory();
    const [username, setUsername] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [isAdmin, setIsAdmin] = useState(false);
    const [ready, setReady] = useState(false);
    let { data, isPending, error } = getUser();
    // console.log(userData.data);

    const setFields = function () {
        console.log('what you are looking for');
        console.log(data);
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
               if (usr.isAdmin) {
                   setIsAdmin(usr.isAdmin);
               }
               if (usr.admin) {
                  setIsAdmin(usr.admin);
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

    const submitDetails = function (event) {
        event.preventDefault();
        event.persist();

        // Verify details
        if (username === '' || firstName === '' || lastName === '' || email === '') {
            message.error('some fields are empty');
        } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(email)) {
            message.error('Invalid email');
        } else {
            let usr = null;
            if (data) {
                usr = data.user[0];
            }
            if (usr) {
                const requestBody = {
                    id: usr.id,
                    username: usr.username,
                    firstName: usr.firstName,
                    lastName: usr.lastName,
                    email: usr.email,

                };

                fetch(`${process.env.REACT_APP_BACKEND_HOST}:${process.env.REACT_APP_BACKEND_PORT}/user/updateProfile`,
                    {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(requestBody),
                    }).then((res) => {
                    if (!res.ok) {
                        throw Error(res.error());
                    }
                    return res.json();
                })
                    .then((response) => {
                        if (response.success) {
                           message.success(response.message);
                        } else {
                            message.error(response.message);
                        }
                    })
                    .catch((err) => {
                        if (err.name === 'AbortError') {
                            console.log('Fetch Aborted');
                        } else {
                            message.error(err.message);
                        }
                    });
            }
        }
    };

    const formik = useFormik({
        initialValues: {
            username: username,
            firstName: firstName,
            lastName: lastName,
            email: email,
        },
        // validate: validateChanges,
        onSubmit: (values) => {
            if (username === '' || firstName === '' || lastName === '' || email === '') {
                message.error('some fields are empty');
            } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(email)) {
                message.error('Invalid email');
            } else {
                const abortCond = new AbortController();

                const usr = {
                    id: data.user[0].id,
                    username: values.username,
                    firstName: values.firstName,
                    lastName: values.lastName,
                    email: values.email,
                };
                const requestOptions = {
                    signal: abortCond.signal,
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(usr),
                };

                fetch('/user/updateProfile', requestOptions)
                    .then((res) => {
                        if (!res.ok) {
                            throw Error(res.error());
                        }
                        return res.json();
                    })
                    .then((data) => {
                        if (data.success) {
                            message.success(data.message);
                        } else {
                            message.error(data.message);
                        }
                    })
                    .catch((err) => {
                        if (err.name === 'AbortError') console.log('Fetch Aborted');
                        else {
                            message.error(err.message);
                        }
                    });
            }
        },
    });

    return (
        <>
            <div id="profile-page-container">
                {isPending && (<div>fetching user information</div>)}
                {error && (<div>could not load user data</div>)}
                {data && !ready && setFields() && setReady(true)}
                {data && ready && (
                <form onSubmit={(e)=> formik.handleSubmit(e)} className="profile form">
                    <div className="heading"> General</div>
                    <Divider />
                    <label htmlFor="username">Username</label>
                    <Input
                      size="default"
                      placeholder="username"
                      name="username"
                      type="text"
                      value={formik.values.username || username}
                      onChange={(event) => {
                          setUsername(event.target.value);
                          formik.handleChange(event);
                      }}
                    />
                    <label htmlFor="firstName">First Name</label>

                    <input
                      id="firstName"
                      name="firstName"
                      type="text"
                      onChange={(event) => {
                            setFirstName(event.target.value);
                            formik.handleChange(event);
                        }}
                      value={formik.values.firstName || firstName}
                    />
                    <label htmlFor="lastName">Last Name</label>
                    <Input
                      size="default"
                      placeholder="Last Name"
                      name="lastName"
                      type="text"
                      value={formik.values.lastName || lastName}
                      onChange={(event) => {
                          setLastName(event.target.value);
                          formik.handleChange(event);
                      }}
                    />
                    <label htmlFor="email">Email</label>
                    <Input
                      size="default"
                      placeholder="Email"
                      name="email"
                      id="email"
                      type="text"
                      value={email}
                      onChange={(event) => {
                          setEmail(event.target.value);
                          formik.handleChange(event);
                      }}
                    />
                    <div className="heading second">Administration</div>
                    <Divider />
                    <div className="switch_box box_1">
                        <input name="admin-switch" type="checkbox" checked={isAdmin} disabled className="switch_1" />
                        <label id="admin-label" className="field-name" htmlFor="Admin-switch">Administrator</label>
                    </div>
                    <div>
                        <Button
                          className="btn submit btn-primary profile-submit-button"
                          type="primary"
                          htmlType="submit"
                          // onClick={(e)=>submitDetails(e)}
                        >
                            Submit
                        </Button>
                    </div>
                </form>
)}
            </div>
        </>
    );
}
