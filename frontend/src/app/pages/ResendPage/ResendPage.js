import { useFormik } from 'formik';
import React, { Component } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import {
    Form, Input, Card, Divider, message, Button,
} from 'antd';
import './resendPage.css';
import ResendButton from './ResendButton';

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

const ResendPage = () => {
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
            fetch(`${process.env.REACT_APP_BACKEND_HOST}/user/resend`, requestOptions)
                .then((res) =>{
                    if (!res.ok) {
                        throw Error(res.error);
                    }
                    return res.json();
                })
                .then((data) => {
                    console.log(data);
                    if (data.status.toLowerCase() === 'ok') {
                        if (data.data.success) {
                            message.success(data.data.message);
                            history.go(-1);
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
            <div id="login-custom-bg">
                <div id="top-left-block" />
                <div id="top-right-block" />
                <div id="bottom-left-block" />
                <div id="bottom-right-block" />
            </div>
            <div id="verify-background">
                <div id="verify-form-container">
                    <div id="verify-card">
                        <div id="verify-card-title">Resend Verification Code</div>
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
                                <ResendButton />
                            </Form.Item>

                            <Divider className="or_divider">
                                OR
                            </Divider>

                            <Form.Item>
                                <Link to="/verify" className="register_link">
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

export default ResendPage;
