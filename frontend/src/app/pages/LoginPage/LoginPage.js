import React, {Component} from 'react';
import {Form, Input, Button, Checkbox, Card, Divider} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import {Link, useHistory} from "react-router-dom";
import {validateLoginDetails} from "./functions/validateLoginDetails";
import LoginButton from "./LoginButton";
import {useFormik} from 'formik';


//Validation Function
const validate = (values) => {
    const errors = {};

    //email validation
    if (!values.email) {
        errors.email = 'Required';
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
        errors.email = 'Invalid email address';
    }

    //password validation

    return errors;
}


const LoginPage = () => {
    let history = useHistory();
    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
        },
        validate,
        onSubmit: values => {
            alert(JSON.stringify(values, null, 2));
            history.push('/') ;

            /*
                Some ASync Function


            Make Get Request

            Update Client if get request Unsuccesfull

            else, redirect to home page
             */
        },
    });

    return (
        <Card
            id={"login_card"}
            className={"loginCard"}
            title="Data Intelligence Suite"
        >
            <form onSubmit={formik.handleSubmit}>

                <Form.Item
                     name="email"
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
                    {formik.touched.email && formik.errors.email ? (
                        <p>{formik.errors.email}</p>) : null}
                </Form.Item>

                <Form.Item
                    name="password"
                >
                    <Input.Password
                        id="password"
                        name="password"
                        type="password"
                        placeholder="Password"
                        value={formik.values.password}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur} //When the user leaves the form
                        prefix={<LockOutlined className="site-form-item-icon"/>}
                    />
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

                <Form.Item
                    className={"forgot_password_link_container"}
                >
                    <a className="forgot_password_link" href="">
                        Forgot password
                    </a>
                </Form.Item>
            </form>
        </Card>
    );
};


export default LoginPage;
