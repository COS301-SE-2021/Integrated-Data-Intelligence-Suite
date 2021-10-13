import React, { useState } from 'react';
import {
 Button, Form, Input, message,
} from 'antd';
import { useFormik } from 'formik';
import { useHistory } from 'react-router-dom';
import RegisterButton from './RegisterButton/RegisterButton';

// Validation Function
const validate = (values) => {
    const errors = {};

    // email validation
    if (!values.email) {
        errors.email = 'Required';
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
        errors.email = 'Invalid email address';
    }

    // firstname validation
    if (!values.firstName) {
        errors.firstName = 'Required';
    }

    // lastname validation
    if (!values.lastName) {
        errors.lastName = 'Required';
    }

    // lastname validation
    if (!values.username) {
        errors.username = 'Required';
    }

    // password validation
    if (!values.password) {
        errors.password = 'required';
    }

    // confirmed password validation
    if (values.confirmedPassword !== values.password) {
        errors.confirmedPassword = 'passwords don\'t match';
    }
    return errors;
};

const RegisterCard = () => {
    const [registerLoading, setRegisterLoading] = useState(false);
    const history = useHistory();

    const formik = useFormik({
        initialValues: {
            email: '',
            lastName: '',
            username: '',
            password: '',
            firstName: '',
            confirmedPassword: '',
        },
        validate,
        onSubmit: (values) => {
            setRegisterLoading(true);

            const requestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(values),
            };
            fetch(`${process.env.REACT_APP_BACKEND_HOST}/user/register`, requestOptions)
            .then((res) =>{
                if (!res.ok) {
                    throw Error(res.error);
                }
                return res.json();
            })
            .then((data) => {
                setRegisterLoading(false);

                if (data.status.toLowerCase() === 'ok') {
                    if (data.data.success) {
                        history.push('/verify');
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

            // use this to go to another page after successful validation server-side
        },

    });
    return (
        <>
            <div id="register-card" className="fading-form-div register disabled">
                <div id="login-card-title">Register.....</div>
                <form onSubmit={formik.handleSubmit} id="register-form" className="form-wrapper">
                    <Form.Item
                      className="input_item_div"
                    >
                        <Input
                          id="firstName"
                          name="firstName"
                          type="text"
                          placeholder="Enter your first name"
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          value={formik.values.firstName}
                        />
                        {
                            formik.touched.firstName && formik.errors.firstName
                                ?
                                (
                                    <p>{formik.errors.firstName}</p>
                                ) : null
                        }
                    </Form.Item>

                    <Form.Item
                      className="input_item_div"
                    >
                        <Input
                          id="lastName"
                          name="lastName"
                          type="text"
                          placeholder="Enter your last name"
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          value={formik.values.lastName}
                        />
                        {
                            formik.touched.lastName && formik.errors.lastName
                                ?
                                (
                                    <p>{formik.errors.lastName}</p>
                                ) : null
                        }
                    </Form.Item>

                    <Form.Item
                      className="input_item_div"
                    >
                        <Input
                          id="username"
                          name="username"
                          type="text"
                          placeholder="Enter your username"
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          value={formik.values.username}
                        />
                        {
                            formik.touched.username && formik.errors.username
                                ?
                                (
                                    <p>{formik.errors.username}</p>
                                ) : null
                        }
                    </Form.Item>

                    <Form.Item
                      className="input_item_div"
                    >
                        <Input
                          id="email"
                          name="email"
                          type="email"
                          placeholder="Enter your email address"
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          value={formik.values.email}
                        />
                        {
                            formik.touched.email && formik.errors.email
                                ?
                                (
                                    <p>{formik.errors.email}</p>
                                ) : null
                        }
                    </Form.Item>

                    <Form.Item
                      className="input_item_div"
                    >
                        <Input.Password
                          id="password"
                          name="password"
                          type="password"
                          placeholder="Enter your password"
                          value={formik.values.password}
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                        />
                        {
                            formik.touched.password && formik.errors.password
                                ?
                                (
                                    <p>{formik.errors.password}</p>
                                ) : null
                        }
                    </Form.Item>

                    <Form.Item
                      className="input_item_div"
                    >
                        <Input.Password
                          id="confirmedPassword"
                          name="confirmedPassword"
                          type="password"
                          placeholder="Rewrite the password"
                          value={formik.values.confirmedPassword}
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                        />
                        {
                            formik.touched.confirmedPassword && formik.errors.confirmedPassword
                                ?
                                (
                                    <p>{formik.errors.confirmedPassword}</p>
                                ) : null
                        }
                    </Form.Item>

                    <Form.Item>
                        <Button
                          type="primary"
                          htmlType="submit"
                          className="login_button"
                          loading={registerLoading}
                        >
                            Register
                        </Button>
                    </Form.Item>

                </form>
            </div>
        </>
    );
};

export default RegisterCard;
