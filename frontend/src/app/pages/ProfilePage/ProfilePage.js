import React, { useEffect, useState } from 'react';
import './ProfilePage.css';
import {
    Button,
    Checkbox, Divider, Input, message,
} from 'antd';
import { useRecoilValue } from 'recoil';
import { useHistory } from 'react-router-dom';
import { Formik, useFormik } from 'formik';
import useGet from '../../functions/useGet';
import { userState } from '../../assets/AtomStore/AtomStore';
import SideBar from '../../components/SideBar/SideBar';

export default function ProfilePage() {
    const history = useHistory();
    const localUser = useRecoilValue(userState);
     const [username, setUsername] = useState('');
     const [firstName, setFirstName] = useState('');
     const [lastName, setLastName] = useState('');
     const [email, setEmail] = useState('');
     const [isAdmin, setIsAdmin] = useState(false);
     const [ready, setReady] = useState(false);

    const getUser = (localUser) =>{
        if (localUser === null) {
            history.push('/login');
            return [{ data: null, isPending: false, error: 'please login ro see profile' }];
        }
        // console.log('local user is ', localUser);
        if (localUser && localUser.id === 'b5aa283d-35d1-421d-a8c6-42dd3e115463') {
            return [{ data: localUser, isPending: false, error: false }];
        }
        const obj = useGet(`/user/getUser/${localUser.id}`);
        return obj;
    };

     const { data, isPending, error } = getUser(localUser);

    const setFields = function () {
        console.log('changing');
        // console.log(data);
        if (data) {
            if (data.status.toLowerCase() === 'ok' && data.data.success) {
                const usr = data.data.user[0];
                console.log(usr);
                if (usr.firstName) {
                    setFirstName(usr.firstName || '');
                }
                if (usr.lastName) {
                    setLastName(usr.lastName || '');
                }
                if (usr.email) {
                    setEmail(usr.email || '');
                }
                if (usr.username) {
                    setUsername(usr.username || '');
                }
                if (usr.isAdmin) {
                    setIsAdmin(usr.isAdmin);
                }
                if (usr.admin) {
                    setIsAdmin(usr.admin || '');
                }
            }
        }
        setReady(true);
        return true;
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
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(usr),
                };

                fetch(`${process.env.REACT_APP_BACKEND_HOST}/user/updateProfile`, requestOptions)
                    .then((res) => {
                        if (!res.ok) {
                            throw Error(res.error);
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
            <div className="default-page-container profile-page">
                <SideBar currentPage="7" />
                {data !== null && !ready && setFields()}
                <div className="reports-content-section">
                    <div className="content-page-title">Profile</div>
                    <div className="profile-card" id="profile-page-container">
                        {
                            ready &&
                             (
                                 <form onSubmit={(e)=> formik.handleSubmit(e)} className="profile form">
                                     <div className="heading"> General</div>
                                     <Divider />
                                     <label htmlFor="username">Username</label>
                                     <br />
                                     <Input
                                       className="simple-input-box"
                                       size="default"
                                       placeholder="username"
                                       name="username"
                                       type="text"
                                       value={formik.values.username || username}
                                       onBlur={formik.handleBlur}
                                       onChange={(event) => {
                                             setUsername(event.target.value);
                                             formik.handleChange(event);
                                         }}
                                     />
                                     <label htmlFor="firstName">First Name</label>
                                     <br />

                                     <Input
                                       size="default"
                                       placeholder="First Name"
                                       name="firstName"
                                       type="text"
                                       onChange={(event) => {
                                             setFirstName(event.target.value);
                                             formik.handleChange(event);
                                         }}
                                       value={formik.values.firstName || firstName}
                                     />
                                     <label htmlFor="lastName">Last Name</label>
                                     <br />
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
                                     <br />
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
                                         <br />
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

                             )
                         }
                    </div>

                </div>
            </div>
        </>
     );
}
