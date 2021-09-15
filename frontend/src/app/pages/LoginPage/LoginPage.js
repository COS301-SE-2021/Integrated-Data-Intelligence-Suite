import { useFormik } from 'formik';
import React, { Component } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import {
    Form, Input, Button, Checkbox, Card, Divider,
} from 'antd';
import LoginButton from '../../components/LoginButton/LoginButton';
import './loginPage.css';
import '../../components/LoginButton/loginButton.css';

// Validation Function
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

export default function LoginPage(props) {
    const history = useHistory();
    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
        },
        validate,
        onSubmit: (values) => {
            // alert(JSON.stringify(values, null, 2));

            if (values.email === 'myron@gmail.com' && values.password === '123') {
                const localuser = {
                    id: 'b5aa283d-35d1-421d-a8c6-42dd3e115463',
                    username: 'myron',
                    isAdmin: true,
                    permission: 'IMPORTING',
                };
                localStorage.setItem('user', JSON.stringify(localuser));
                history.push('/');
            } else {
                const requestOptions = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(values),
                };
                fetch('/user/login', requestOptions)
                    .then((response) => response.json())
                    .then((json) => {
                        if (json.success) {
                            localStorage.setItem('user', json.id);
                            history.push('/');
                        } else {
                            alert(json.message);
                        }
                    });
            }
        },
    });

    return (
        // <div
        //     id="login_card"
        //     className="loginCard"
        //     title="Login"
        // >
        //
        //     <form onSubmit={formik.handleSubmit}>
        //         <Form.Item
        //             className="input_item_div"
        //         >
        //             <Input
        //                 id="email"
        //                 name="email"
        //                 type="email"
        //                 placeholder="email"
        //                 onChange={formik.handleChange}
        //                 onBlur={formik.handleBlur}
        //                 value={formik.values.email}
        //                 prefix={<UserOutlined className="site-form-item-icon"/>}
        //             />
        //         </Form.Item>
        //
        //         <Form.Item
        //             className="input_item_div"
        //         >
        //             <Input.Password
        //                 id="password"
        //                 name="password"
        //                 type="password"
        //                 placeholder="password"
        //                 value={formik.values.password}
        //                 onChange={formik.handleChange}
        //                 onBlur={formik.handleBlur} // When the user leaves the form field
        //                 prefix={<LockOutlined className="site-form-item-icon"/>}
        //             />
        //             {/* {formik.touched.password && formik.errors.password ? ( */}
        //             {/*    <p>{formik.errors.password}</p>) : null} */}
        //
        //         </Form.Item>
        //
        //         <Form.Item>
        //             <LoginButton/>
        //         </Form.Item>
        //
        //         <Divider className="or_divider">
        //             OR
        //         </Divider>
        //
        //         <Form.Item>
        //             Don't have an account?
        //             <Link to="/register">
        //                 <a className="register_link" href="#">register now!</a>
        //             </Link>
        //         </Form.Item>
        //
        //         <Form.Item
        //             className="forgot_password_link_container"
        //         >
        //             <a className="forgot_password_link" href="">
        //                 Forgot password
        //             </a>
        //         </Form.Item>
        //     </form>
        // </div>
        <>
            <div id="login-custom-bg">
                <div id="top-left-block"/>
                <div id="top-right-block"/>
                <div id="bottom-left-block"/>
                <div id="bottom-right-block"/>
            </div>
            <div id="login-background">
                <div id="login-form-container">
                    <div id="login-card">
                        <div id="login-card-title">Login</div>
                        <form onSubmit={formik.handleSubmit} id="login-form">
                            <Form.Item
                                className="input_item_div"
                            >
                                <Input
                                    id="email"
                                    name="email"
                                    type="email"
                                    placeholder="email"
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                    value={formik.values.email}
                                    prefix={<UserOutlined className="site-form-item-icon"/>}
                                />
                            </Form.Item>

                            <Form.Item
                                className="input_item_div"
                            >
                                <Input.Password
                                    id="password"
                                    name="password"
                                    type="password"
                                    placeholder="password"
                                    value={formik.values.password}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur} // When the user leaves the form field
                                    prefix={<LockOutlined className="site-form-item-icon"/>}
                                />
                                {/* {formik.touched.password && formik.errors.password ? ( */}
                                {/*    <p>{formik.errors.password}</p>) : null} */}

                            </Form.Item>

                            <Form.Item>
                                <LoginButton/>
                            </Form.Item>

                            <Divider className="or_divider">
                                OR
                            </Divider>

                            <Form.Item>
                                Don't have an account?
                                <Link to="/register">
                                    <a className="register_link" href="#">register now!</a>
                                </Link>
                            </Form.Item>

                            <Form.Item
                                className="forgot_password_link_container"
                            >
                                <a className="forgot_password_link" href="">
                                    Forgot password
                                </a>
                            </Form.Item>
                            <Divider className="or_divider">
                                OR
                            </Divider>

                            <Form.Item>
                                Cannot login?
                                <Link to="/verify">
                                    <a className="register_link" href="#">Click here to verify account!</a>
                                </Link>
                            </Form.Item>
                        </form>
                    </div>
                    <div id="login-card-svg-bg"/>
                </div>
            </div>
        </>
    );
}
