import { useFormik } from 'formik';
import React, { Component } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import {
  Form, Input, Button, Checkbox, Card, Divider, message,
} from 'antd';
import ResetPasswordButton from './ResetPasswordButton';

// Validation Function
const validate = (values) => {
  const errors = {};

  // email validation
  if (!values.email) {
    errors.email = 'Required';
  } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
    errors.email = 'Invalid email address';
  }

  // lastname validation
  if (!values.otp) {
    errors.otp = 'Required';
  }

  // password validation
  if (!values.newPassword) {
    errors.newPassword = 'required';
  }

  // confirmed password validation
  if (values.confirmedpassword !== values.newPassword) {
    errors.confirmedpassword = 'passwords don\'t match';
  }
  return errors;
};

function getLocalEmail() {
    const localUser = localStorage.getItem('email');
    if (localUser) {
        return localUser;
    }
    return null;
}

const ResetPasswordPage = () => {
  const localUser = getLocalEmail();
  const history = useHistory();
  const formik = useFormik({
    initialValues: {
      email: '',
      newPassword: '',
      otp: '',
    },
    validate,
    onSubmit: (values) => {
      // alert(JSON.stringify(values, null, 2));
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(values),
      };
      fetch(`${process.env.REACT_APP_BACKEND_HOST}/user/resetPassword`, requestOptions)
          .then((res) =>{
              if (!res.ok) {
                  throw Error(res.error);
              }
              return res.json();
          })
          .then((data)=>{
              console.log(data);
              // setIsLoading(false);
              if (data.status.toLowerCase() === 'ok') {
                  if (data.data.success) {
                      message.success(data.data.message)
                          .then(()=>history.push('/login'));
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
                      <div id="verify-card-title">Reset you password</div>
                      <form onSubmit={formik.handleSubmit}>
                          <Form.Item
                            className="input_item_div"
                          >
                              <Input
                                id="otp"
                                name="otp"
                                type="text"
                                placeholder="Enter your OTP"
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                                value={formik.values.otp}
                              />
                              {formik.touched.otp && formik.errors.otp ? (
                                  <p>{formik.errors.otp}</p>) : null}
                          </Form.Item>

                          <Form.Item
                            className="input_item_div"
                          >
                              <Input.Password
                                id="newPassword"
                                name="newPassword"
                                type="password"
                                placeholder="Enter your new password"
                                value={formik.values.newPassword}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                              />
                              {formik.touched.newPassword && formik.errors.newPassword ? (
                                  <p>{formik.errors.newPassword}</p>) : null}
                          </Form.Item>

                          <Form.Item
                            className="input_item_div"
                          >
                              <Input.Password
                                id="confirmedpassword"
                                name="confirmedpassword"
                                type="password"
                                placeholder="Rewrite the password"
                                value={formik.values.confirmedpassword}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                              />
                              {/* eslint-disable-next-line max-len */}
                              {formik.touched.confirmedpassword && formik.errors.confirmedpassword ? (
                                  <p>{formik.errors.confirmedpassword}</p>) : null}
                          </Form.Item>

                          <Form.Item>
                              <ResetPasswordButton />
                          </Form.Item>

                          <Divider className="or_divider">
                              OR
                          </Divider>

                          <Form.Item>
                              Already have an account?
                              <Link to="/login" className="register_link">
                                  Login!
                              </Link>
                          </Form.Item>

                          {/* <Form.Item */}
                          {/*    className={"forgot_password_link_container"} */}
                          {/* > */}
                          {/*    <a className="forgot_password_link" href=""> */}
                          {/*        Forgot passw */}
                          {/*    </a> */}
                          {/* </Form.Item> */}
                      </form>
                  </div>
                  <div id="login-card-svg-bg" />
              </div>
          </div>
      </>
  );
};

export default ResetPasswordPage;
