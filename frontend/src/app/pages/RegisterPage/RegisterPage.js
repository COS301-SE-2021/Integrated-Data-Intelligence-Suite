import {useFormik} from 'formik';
import React, {Component} from 'react';
import RegisterButton from "../../components/RegisterButton/RegisterButton";
import {Link, useHistory} from "react-router-dom";
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import {Form, Input, Button, Checkbox, Card, Divider} from 'antd';
import './registerPage.css';


//Validation Function
const validate = (values) => {
    const errors = {};

    //email validation
    if (!values.email) {
        errors.email = 'Required';
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
        errors.email = 'Invalid email address';
    }

    //firstname validation
    if (!values.firstName) {
        errors.firstName = 'Required';
    }

    //lastname validation
    if (!values.lastName) {
        errors.lastName = 'Required';

    }

    //lastname validation
    if (!values.username) {
        errors.username = 'Required';
    }

    //password validation
    if (!values.password) {
        errors.password = 'required'
    }

    //confirmed password validation
    if (values.confirmedpassword !== values.password) {
        errors.confirmedpassword = 'passwords don\'t match';
    }
    return errors;
}


const RegisterPage = () => {
    localStorage.clear();
    let history = useHistory();
    const formik = useFormik({
        initialValues: {
            email: '',
            lastName: '',
            username: '',
            password: '',
            firstName: ''
        },
        validate,
        onSubmit: values => {
            //alert(JSON.stringify(values, null, 2));
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(values)
            };
            fetch('/user/register', requestOptions)
                .then(response => {
                    return response.json()
                }).then(json => {
                    if(json.success) {
                        history.push('/');
                    }else{
                        alert(json.message)
                    }
                });

            //use this to go to another page after successful validation server-side

        },

    });

    return (
        <div className={"background gradient register"}>
        <Card
            id={"register_card"}
            className={"loginCard"}
            title="Register"
        >
            <form onSubmit={formik.handleSubmit}>

                <Form.Item
                    label={'First Name'}
                >
                    <Input
                        id="firstName"
                        name="firstName"
                        type="text"
                        placeholder="first name"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.firstName}
                        // prefix={<UserOutlined className="site-form-item-icon"/>}
                    />
                    {formik.touched.firstName && formik.errors.firstName ? (
                        <p>{formik.errors.firstName}</p>) : null}
                </Form.Item>

                <Form.Item
                    label={'Last Name'}
                >
                    <Input
                        id="lastName"
                        name="lastName"
                        type="text"
                        placeholder="last name"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.lastName}
                        // prefix={<UserOutlined className="site-form-item-icon"/>}
                    />
                    {formik.touched.lastName && formik.errors.lastName ? (
                        <p>{formik.errors.lastName}</p>) : null}
                </Form.Item>

                <Form.Item
                    label={'User name'}
                >
                    <Input
                        id="username"
                        name="username"
                        type="text"
                        placeholder="user name"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.username}
                        // prefix={<UserOutlined className="site-form-item-icon"/>}
                    />
                    {formik.touched.username && formik.errors.username ? (
                        <p>{formik.errors.username}</p>) : null}
                </Form.Item>

                <Form.Item
                    label={'email'}
                >
                    <Input
                        id="email"
                        name="email"
                        type="email"
                        placeholder="email"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.email}
                        // prefix={<UserOutlined className="site-form-item-icon"/>}
                    />
                    {formik.touched.email && formik.errors.email ? (
                        <p>{formik.errors.email}</p>) : null}
                </Form.Item>

                <Form.Item
                    label={'password'}
                >
                    <Input.Password
                        id="password"
                        name="password"
                        type="password"
                        placeholder="Password"
                        value={formik.values.password}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur} //When the user leaves the form
                        // prefix={<LockOutlined className="site-form-item-icon"/>}
                    />
                    {formik.touched.password && formik.errors.password ? (
                        <p>{formik.errors.password}</p>) : null}

                </Form.Item>

                <Form.Item
                    label={'Confirm password'}
                >
                    <Input.Password
                        id="confirmedpassword"
                        name="confirmedpassword"
                        type="password"
                        placeholder="rewrite the password"
                        value={formik.values.confirmedpassword}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur} //When the user leaves the form
                        // prefix={<LockOutlined className="site-form-item-icon"/>}
                    />
                    {formik.touched.confirmedpassword && formik.errors.confirmedpassword ? (
                        <p>{formik.errors.confirmedpassword}</p>) : null}

                </Form.Item>

                <Form.Item>
                    <RegisterButton/>
                </Form.Item>

                <Divider className={'or_divider'}>
                    OR
                </Divider>

                <Form.Item>
                    Already have an account?
                    <Link to={"/login"}>
                        <a className={"register_link"} href="#">login</a>
                    </Link>
                </Form.Item>

                {/*<Form.Item*/}
                {/*    className={"forgot_password_link_container"}*/}
                {/*>*/}
                {/*    <a className="forgot_password_link" href="">*/}
                {/*        Forgot passw*/}
                {/*    </a>*/}
                {/*</Form.Item>*/}
            </form>
        </Card>
        </div>
    );
};


export default RegisterPage;
