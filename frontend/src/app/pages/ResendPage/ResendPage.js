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
            fetch('/user/resend', requestOptions)
                .then((response) => response.json()).then((json) => {
                if (json.success) {
                    // localStorage.setItem("user", json.id)
                    message.success(json.message);
                    history.go(-1);
                } else {
                    message.error(json.message);
                }
            });
        },
    });

    return (
        // <Card
        //   id="login_card"
        //   className="loginCard"
        //   title="Step 2: Verify your account"
        // >
        //
        //     <form onSubmit={formikVerify.handleSubmit}>
        //         <Form.Item
        //           className="input_item_div"
        //         >
        //             <Input
        //               id="email"
        //               name="email"
        //               type="email"
        //               placeholder="Email"
        //               onChange={formikVerify.handleChange}
        //               onBlur={formikVerify.handleBlur}
        //               value={formikVerify.values.email}
        //               prefix={<UserOutlined className="site-form-item-icon" />}
        //             />
        //         </Form.Item>
        //
        //         <Form.Item
        //           className="input_item_div"
        //         >
        //             <Input
        //               id="verificationCode"
        //               name="verificationCode"
        //               type="text"
        //               placeholder="Verification code"
        //               value={formikVerify.values.verificationCode}
        //               onChange={formikVerify.handleChange}
        //               onBlur={formikVerify.handleBlur} // When the user leaves the form field
        //               prefix={<LockOutlined className="site-form-item-icon" />}
        //             />
        //             {/* {formik.touched.password && formik.errors.password ? ( */}
        //             {/*    <p>{formik.errors.password}</p>) : null} */}
        //
        //         </Form.Item>
        //
        //         <Form.Item>
        //             <VerifyButton />
        //         </Form.Item>
        //
        //         <Divider className="or_divider">
        //             OR
        //         </Divider>
        //     </form>
        //
        //     <form onSubmit={formikResend.handleSubmit}>
        //         <Form.Item>
        //             <ResendButton />
        //         </Form.Item>
        //     </form>
        //
        // </Card>

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
                        </form>

                        <Button
                          type="link"
                          className="link-button"
                          href="/verify"
                        >
                            Go back
                        </Button>
                    </div>
                    <div id="login-card-svg-bg" />
                </div>
            </div>
        </>
    );
};

export default ResendPage;
