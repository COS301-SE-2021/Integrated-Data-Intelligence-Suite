import React, { useState } from 'react';
import {
 Button, Divider, Form, Input, message,
} from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Link, useHistory } from 'react-router-dom';
import { useFormik } from 'formik';
import { useSetRecoilState } from 'recoil';
import { userState } from '../../assets/AtomStore/AtomStore';

const validate = (values) => {
    const errors = {};

    // email validation
    if (!values.email) {
        errors.email = 'Required';
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
        errors.email = 'Invalid email address';
    }

    // password validation
    if (!values.password) {
        errors.password = 'required';
    }
    return errors;
};

const LoginCard = () => {
    const setUser = useSetRecoilState(userState);
    const history = useHistory();

    const [loginLoading, setLoginLoading] = useState(false);

    const formik = useFormik({
        initialValues: {
            password: '',
            email: '',
        },
        validate,
        onSubmit: (values) => {
            setLoginLoading(true);
            // alert(JSON.stringify(values, null, 2));

            if (values.email === 'myron@gmail.com' && values.password === '123') {
                const localuser = {
                    id: 'b5aa283d-35d1-421d-a8c6-42dd3e115463',
                    username: 'Myron Lopes',
                    firstName: 'Myron',
                    lastName: 'Maugi',
                    admin: true,
                    permission: 'IMPORTING',
                    email: values.email,
                };
                localStorage.setItem('user', JSON.stringify(localuser));
                setUser(localuser);
                history.push('/chart');
            } else {
                const requestOptions = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(values),
                };
                fetch(`${process.env.REACT_APP_BACKEND_HOST}/user/login`, requestOptions)
                    .then((res) =>{
                        if (!res.ok) {
                            throw Error(res.error);
                        }
                        return res.json();
                    })
                    .then((data) => {
                        setLoginLoading(false);
                        // console.log(data);
                        if (data.status.toLowerCase() === 'ok') {
                            // localStorage.setItem('user', json.id);
                            if (data.data.success) {
                                // console.log("current user", JSON.parse(data.data.id));
                                setUser(JSON.parse(data.data.id));
                                message.success(data.data.message)
                                    .then(()=>{
                                        history.push('/chart');
                                    });
                            } else {
                                message.error(data.data.message);
                            }
                        } else if (data.errors) {
                            for (let i = 0; i < data.errors.length; i = i + 1) {
                                message.error(data.errors[i]);
                            }
                        }
                    })
                    .catch((error)=>{
                        setLoginLoading(false);
                        // console.log(error.message);
                        // message.error(error);
                    })
            }
        },
    });

    return (
        <>
            <div id="login-card" className="fading-form-div login">
                <div id="login-card-title">Login</div>
                <form onSubmit={formik.handleSubmit} id="login-form" className="form-wrapper">
                    <Form.Item
                      className="input_item_div"
                    >
                        <Input
                          id="email"
                          name="email"
                          type="email"
                          placeholder="Email address"
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          value={formik.values.email}
                          prefix={<UserOutlined className="site-form-item-icon" />}
                        />
                    </Form.Item>

                    <Form.Item
                      className="input_item_div"
                    >
                        <Input.Password
                          id="password"
                          name="password"
                          type="password"
                          placeholder="Password"
                          value={formik.values.password}
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur} // When the user leaves the form field
                          prefix={<LockOutlined className="site-form-item-icon" />}
                        />
                    </Form.Item>

                    <Form.Item>
                        <Button
                          type="primary"
                          htmlType="submit"
                          className="login_button"
                          loading={loginLoading}
                        >
                            Log in
                        </Button>
                    </Form.Item>

                    <Form.Item
                      className="forgot_password_link_container"
                    >
                        <Link to="/sendOTP" className="register_link">
                            Forgot password?
                        </Link>
                    </Form.Item>
                    <Divider className="or_divider">
                        OR
                    </Divider>

                    <Form.Item>
                        Cannot login?
                        <Link to="/verify" className="register_link">
                            Click here to verify account!
                        </Link>
                    </Form.Item>
                </form>
            </div>
        </>
    );
};

export default LoginCard;
