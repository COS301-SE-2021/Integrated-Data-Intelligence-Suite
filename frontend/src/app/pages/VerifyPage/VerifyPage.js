import { useFormik } from 'formik';
import React, { Component, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import {
    Form, Input, Card, Divider, message, Button,
} from 'antd';
import VerifyButton from './VerifyButton';
import './verifyPage.css';

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
    if (!values.verificationCode) {
        errors.verificationCode = 'required';
    }
    return errors;
};

const VerifyPage = () => {
    const [isLoading, setIsLoading] = useState(false);
    const history = useHistory();
    const formikVerify = useFormik({
        initialValues: {
            email: '',
            verificationCode: '',
        },
        validate,
        onSubmit: (values) => {
            setIsLoading(true);

            const requestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(values),
            };
            fetch(`${process.env.REACT_APP_BACKEND_HOST}/user/verify`, requestOptions)
                .then((res) =>{
                    if (!res.ok) {
                        throw Error(res.error);
                    }
                    return res.json();
                })
                .then((data)=>{
                    console.log(data);
                    setIsLoading(false);
                    if (data.status.toLowerCase() === 'ok') {
                        if (data.data.success) {
                            message.success(data.data.message)
                                .then(()=>history.push('/login'));
                        } else {
                            message.error(data.data.message);
                            if (data.data.message === 'This account has already been verified') {
                                history.push('/login');
                            }
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
                // .then((response) => response.json()).then((json) => {
                // if (json.success) {
                //     // localStorage.setItem("user", json.id)
                //     message.success(json.message);
                //     history.push('/login');
                // } else {
                //     message.error(json.message);
                //     if (json.message === 'This account has already been verified') {
                //         history.push('/login');
                //     }
                // }
            // });
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
                        <div id="verify-card-title">Step 2: Verify your account</div>
                        <form onSubmit={formikVerify.handleSubmit}>
                            <Form.Item
                              className="input_item_div"
                            >
                                <Input
                                  id="email"
                                  name="email"
                                  type="email"
                                  placeholder="Email address"
                                  onChange={formikVerify.handleChange}
                                  onBlur={formikVerify.handleBlur}
                                  value={formikVerify.values.email}
                                  prefix={<UserOutlined className="site-form-item-icon" />}
                                />
                            </Form.Item>

                            <Form.Item
                              className="input_item_div"
                            >
                                <Input
                                  id="verificationCode"
                                  name="verificationCode"
                                  type="text"
                                  placeholder="Verification code"
                                  value={formikVerify.values.verificationCode}
                                  onChange={formikVerify.handleChange}
                                  onBlur={formikVerify.handleBlur} // When the user leaves the form field
                                  prefix={<LockOutlined className="site-form-item-icon" />}
                                />
                                {/* {formik.touched.password && formik.errors.password ? ( */}
                                {/*    <p>{formik.errors.password}</p>) : null} */}

                            </Form.Item>

                            <Form.Item>
                                <VerifyButton />
                            </Form.Item>

                            <Divider className="or_divider">
                                OR
                            </Divider>

                            <Form.Item>
                                <Link className="back-to-register pink-link" to="/login">
                                    Back to Registration
                                </Link>
                                <Link to="/resend" className="register_link">
                                    Resend code
                                </Link>
                            </Form.Item>
                        </form>
                    </div>
                </div>
            </div>
        </>
    );
};

export default VerifyPage;
