import {useFormik} from 'formik';
import React, {Component} from 'react';
import VerifyButton from "./VerifyButton";
import {Link, useHistory} from "react-router-dom";
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import {Form, Input, Card, Divider} from 'antd';
import '../../../styles/VerifyPage/verifyPage.css';
import ResendButton from "./ResendButton";


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
    if (!values.verificationCode) {
        errors.verificationCode = 'required'
    }
    return errors;
}


const VerifyPage = () => {
    let history = useHistory();
    const formikVerify = useFormik({
        initialValues: {
            email: '',
            verificationCode: '',
        },
        validate,
        onSubmit: values => {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(values)
            };
            fetch('/user/verify', requestOptions)
                .then(response => {
                    return response.json()
                }).then(json => {
                if (json.success) {
                    //localStorage.setItem("user", json.id)
                    history.push('/login');
                } else {
                    alert(json.message)
                }
            });
        },
    });

    const formikResend = useFormik({
        initialValues: {
            email: "thisISTest@gmail.com"
        },
        validate,
        onSubmit: values => {
            alert("sending mail");
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(values)
            };
            fetch('/user/resend', requestOptions)
                .then(response => {
                    return response.json()
                }).then(json => {
                if (json.success) {
                    //localStorage.setItem("user", json.id)
                    alert(json.message);
                } else {
                    alert(json.message);
                }
            });
        },
    });

    return (
        <Card
            id={"login_card"}
            className={"loginCard"}
            title="Verify your account"
        >

            <form onSubmit={formikVerify.handleSubmit}>
                <Form.Item
                    className={'input_item_div'}
                >
                    <Input
                        id="email"
                        name="email"
                        type="email"
                        placeholder="Email"
                        onChange={formikVerify.handleChange}
                        onBlur={formikVerify.handleBlur}
                        value={formikVerify.values.email}
                        prefix={<UserOutlined className="site-form-item-icon"/>}
                    />
                </Form.Item>

                <Form.Item
                    className={'input_item_div'}
                >
                    <Input
                        id="verificationCode"
                        name="verificationCode"
                        type="text"
                        placeholder="Verification code"
                        value={formikVerify.values.verificationCode}
                        onChange={formikVerify.handleChange}
                        onBlur={formikVerify.handleBlur} //When the user leaves the form field
                        prefix={<LockOutlined className="site-form-item-icon"/>}
                    />
                    {/*{formik.touched.password && formik.errors.password ? (*/}
                    {/*    <p>{formik.errors.password}</p>) : null}*/}

                </Form.Item>

                <Form.Item>
                    <VerifyButton/>
                </Form.Item>

                <Divider className={'or_divider'}>
                    OR
                </Divider>
            </form>

            <form onSubmit={formikResend.handleSubmit}>
                <Form.Item>
                    <ResendButton/>
                </Form.Item>
            </form>

        </Card>
    );
};


export default VerifyPage;
