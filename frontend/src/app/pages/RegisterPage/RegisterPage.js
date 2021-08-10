import React, {Component} from 'react';
import {Form, Input, Button, Checkbox, Card, Divider} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import {Link, useHistory} from "react-router-dom";
import LoginButton from "./LoginButton";
import {useFormik} from 'formik';


//Validation Function
const validate = (values) => {
    const errors = {};

    if (!values.email) {
        errors.email = 'Required';
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
        errors.email = 'Invalid email address';
    }

    return errors;
}


const RegisterPage = () => {
    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
        },

        onSubmit: values => {
            alert(JSON.stringify(values, null, 2));

            //Make Get Request

            //Update Client if get request Unsuccesfull

            //else, redirect to home page
        },
    });

    return (
        <Card
            id={"login_card"}
            className={"loginCard"}
            title="Register"
        >
            <form onSubmit={formik.handleSubmit}>

                <Form.Item
                    // label="email"
                >
                    <Input
                        id="email"
                        name="email"
                        type="email"
                        placeholder="email"
                        // onChange={formik.handleChange}
                        // onBlur={formik.handleBlur}
                        value={formik.values.email}
                        prefix={<UserOutlined className="site-form-item-icon"/>}
                    />
                </Form.Item>

                <Form.Item
                    // label="password"
                    name="password"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                        },
                    ]}
                >
                    <Input.Password
                        id="password"
                        name="password"
                        type="password"
                        placeholder="Password"
                        value={formik.values.password}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        prefix={<LockOutlined className="site-form-item-icon"/>}
                    />
                    {formik.touched.email && formik.errors.email ? (
                        <div>{formik.errors.email}</div>) : null}
                </Form.Item>

                <Form.Item>
                    <LoginButton/>
                </Form.Item>

                <Divider className={'or_divider'}>
                    OR
                </Divider>

                <Form.Item>
                    Don't have an account?
                    <Link to={"/register"}>
                        <a className={"register_link"} href="#">register now!</a>
                    </Link>
                </Form.Item>

                <Form.Item>
                    <a className="forgot_password_link" href="">
                        Forgot password
                    </a>
                </Form.Item>
            </form>
        </Card>
    );
};


export default RegisterPage;
