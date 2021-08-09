import React, {Component} from 'react';
import {Form, Input, Button, Checkbox, Card, Divider} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import {Link} from "react-router-dom";


class LoginPage extends React.Component {
    render() {
        return (
            <>
                <Card
                    id={"login_card"}
                    className={"loginCard"}
                    title="Data Intelligence Suite"
                    style={{width: 300}}
                >

                    <Form
                        name="normal_login"
                        className="login-form"
                        initialValues={{
                            remember: true,
                        }}
                        onFinish={null}
                    >
                        <Form.Item
                            name="username"
                            rules={[
                                {
                                    required: false,
                                    message: 'Please input your username!',
                                },
                            ]}
                        >
                            <Input
                                prefix={<UserOutlined className="site-form-item-icon"/>}
                                placeholder="email"
                            />

                        </Form.Item>

                        <Form.Item
                            name="password"
                            rules={[
                                {
                                    required: false,
                                    message: 'Please input your Password!',
                                },
                            ]}
                        >
                            <Input.Password
                                prefix={<LockOutlined className="site-form-item-icon"/>}
                                type="password"
                                placeholder="Password"
                                allowClear={false}
                            />
                        </Form.Item>




                        <Form.Item>
                            <Link to="/">
                                <Button
                                    type="primary"
                                    htmlType="submit"
                                    className="login_button">
                                    Log in
                                </Button>
                            </Link>

                        </Form.Item>

                        <Divider className={'or_divider'}>
                            OR
                        </Divider>

                        <Form.Item>
                            {/*<Form.Item name="remember" valuePropName="checked" noStyle>*/}
                            {/*    <Checkbox>Remember me</Checkbox>*/}
                            {/*</Form.Item>*/}

                            <a className="forgot_password_link" href="">
                                Forgot password
                            </a>
                        </Form.Item>

                        <Form.Item>
                            Don't have an account?
                            <Link to={"/register"}>
                                <a className={"register_link"} href="#">register now!</a>
                            </Link>

                        </Form.Item>

                    </Form>

                </Card>


            </>
        );
    }
}

export default LoginPage;