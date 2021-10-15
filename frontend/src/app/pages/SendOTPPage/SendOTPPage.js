import { useFormik } from 'formik';
import React, { Component } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import {
    Form, Input, Card, Divider, message, Button,
} from 'antd';
import './SendOTPPage.css';
import SendOTPButton from './SendOTPButton';

// Validation Function
const validate = (values) => {
    const errors = {};

    // email validation
    if (!values.email) {
        errors.email = 'Required';
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
        errors.email = 'Invalid email address';
    }
    return errors;
};

const SendOTPPage = () => {
    const history = useHistory();

    const formik = useFormik({
        initialValues: {
            email: '',
        },
        validate,
        onSubmit: (values) => {
            const requestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(values),
            };
            fetch(`${process.env.REACT_APP_BACKEND_HOST}/user/sendOTP`, requestOptions)
                .then((res) => {
                    if (!res.ok) {
                        throw Error(res.error);
                    }
                    return res.json();
                })
                .then((data) => {
                if (data.status.toLowerCase() === 'ok') {
                    if (data.data.success) {
                        history.push('/resetPassword');
                    } else {
                        message.error(data.data.message);
                    }
                } else if (data.errors) {
                    for (let i = 0; i < data.errors.length; i = i + 1) {
                        message.error(data.errors[i]);
                    }
                }
                })
                .catch((error) =>{
                    message.error(error.message);
                });
        },
    });

    return (
        <>
            <div className="area">
                <ul className="circles">
                    <li />
                    <li />
                    <li />
                    <li />
                    <li />
                    <li />
                    <li />
                    <li />
                    <li />
                    <li />
                </ul>
            </div>
            <div id="verify-background">
                <div id="verify-form-container">
                    <div id="verify-card">
                        <div id="verify-card-title">Send OTP</div>
                        <form onSubmit={formik.handleSubmit}>
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

                            <Form.Item>
                                <SendOTPButton />
                            </Form.Item>

                            <Divider className="or_divider">
                                OR
                            </Divider>

                            <Form.Item>
                                <Link to="/login" className="register_link">
                                    Go back
                                </Link>
                            </Form.Item>
                        </form>
                    </div>
                    <div id="login-card-svg-bg" />
                </div>
            </div>
        </>
    );
};

export default SendOTPPage;
