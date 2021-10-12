// import { useFormik } from 'formik';
// import React, { Component } from 'react';
// import { Link, useHistory } from 'react-router-dom';
// import { UserOutlined, LockOutlined } from '@ant-design/icons';
// import {
//   Form, Input, Button, Checkbox, Card, Divider, message,
// } from 'antd';
// import RegisterButton from '../../components/RegisterButton/RegisterButton';
//
// // // Validation Function
// // const validate = (values) => {
// //   const errors = {};
// //
// //   // email validation
// //   if (!values.email) {
// //     errors.email = 'Required';
// //   } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
// //     errors.email = 'Invalid email address';
// //   }
// //
// //   // firstname validation
// //   if (!values.firstName) {
// //     errors.firstName = 'Required';
// //   }
// //
// //   // lastname validation
// //   if (!values.lastName) {
// //     errors.lastName = 'Required';
// //   }
// //
// //   // lastname validation
// //   if (!values.username) {
// //     errors.username = 'Required';
// //   }
// //
// //   // password validation
// //   if (!values.password) {
// //     errors.password = 'required';
// //   }
// //
// //   // confirmed password validation
// //   if (values.confirmedpassword !== values.password) {
// //     errors.confirmedpassword = 'passwords don\'t match';
// //   }
// //   return errors;
// // };
//
// const RegisterPage = () => {
//   localStorage.clear();
//   const history = useHistory();
//   const formik = useFormik({
//     initialValues: {
//       email: '',
//       lastName: '',
//       username: '',
//       password: '',
//       firstName: '',
//     },
//     validate,
//     onSubmit: (values) => {
//       // alert(JSON.stringify(values, null, 2));
//       const requestOptions = {
//         method: 'POST',
//         headers: { 'Content-Type': 'application/json' },
//         body: JSON.stringify(values),
//       };
//       fetch(`${process.env.REACT_APP_BACKEND_HOST}/user/register`, requestOptions)
//         .then((response) => response.json()).then((json) => {
//           if (json.success) {
//             message.success(json.message);
//             localStorage.setItem('email', values.email);
//             history.push('/verify');
//           } else {
//               message.error(json.message);
//           }
//         });
//
//       // use this to go to another page after successful validation server-side
//     },
//
//   });
//
//   return (
//       // <div className="background gradient register">
//       //     <Card
//       //       id="register_card"
//       //       className="loginCard"
//       //       title="Step 1: Register Account"
//       //     >
//       //         <form onSubmit={formik.handleSubmit}>
//       //
//       //             <Form.Item
//       //                 className="input_item_div"
//       //             >
//       //                 <Input
//       //                   id="firstName"
//       //                   name="firstName"
//       //                   type="text"
//       //                   placeholder="Enter your first name"
//       //                   onChange={formik.handleChange}
//       //                   onBlur={formik.handleBlur}
//       //                   value={formik.values.firstName}
//       //                 />
//       //                 {formik.touched.firstName && formik.errors.firstName ? (
//       //                     <p>{formik.errors.firstName}</p>) : null}
//       //             </Form.Item>
//       //
//       //             <Form.Item
//       //                 className="input_item_div"
//       //             >
//       //                 <Input
//       //                   id="lastName"
//       //                   name="lastName"
//       //                   type="text"
//       //                   placeholder="Enter your last name"
//       //                   onChange={formik.handleChange}
//       //                   onBlur={formik.handleBlur}
//       //                   value={formik.values.lastName}
//       //                 />
//       //                 {formik.touched.lastName && formik.errors.lastName ? (
//       //                     <p>{formik.errors.lastName}</p>) : null}
//       //             </Form.Item>
//       //
//       //             <Form.Item
//       //                 className="input_item_div"
//       //             >
//       //                 <Input
//       //                   id="username"
//       //                   name="username"
//       //                   type="text"
//       //                   placeholder="Enter your username"
//       //                   onChange={formik.handleChange}
//       //                   onBlur={formik.handleBlur}
//       //                   value={formik.values.username}
//       //                 />
//       //                 {formik.touched.username && formik.errors.username ? (
//       //                     <p>{formik.errors.username}</p>) : null}
//       //             </Form.Item>
//       //
//       //             <Form.Item
//       //                 className="input_item_div"
//       //             >
//       //                 <Input
//       //                   id="email"
//       //                   name="email"
//       //                   type="email"
//       //                   placeholder="Enter your email address"
//       //                   onChange={formik.handleChange}
//       //                   onBlur={formik.handleBlur}
//       //                   value={formik.values.email}
//       //                 />
//       //                 {formik.touched.email && formik.errors.email ? (
//       //                     <p>{formik.errors.email}</p>) : null}
//       //             </Form.Item>
//       //
//       //             <Form.Item
//       //                 className="input_item_div"
//       //             >
//       //                 <Input.Password
//       //                   id="password"
//       //                   name="password"
//       //                   type="password"
//       //                   placeholder="Enter your password"
//       //                   value={formik.values.password}
//       //                   onChange={formik.handleChange}
//       //                   onBlur={formik.handleBlur}
//       //                 />
//       //                 {formik.touched.password && formik.errors.password ? (
//       //                     <p>{formik.errors.password}</p>) : null}
//       //
//       //             </Form.Item>
//       //
//       //             <Form.Item
//       //                 className="input_item_div"
//       //             >
//       //                 <Input.Password
//       //                   id="confirmedpassword"
//       //                   name="confirmedpassword"
//       //                   type="password"
//       //                   placeholder="Rewrite the password"
//       //                   value={formik.values.confirmedpassword}
//       //                   onChange={formik.handleChange}
//       //                   onBlur={formik.handleBlur}
//       //                 />
//       //                 {formik.touched.confirmedpassword && formik.errors.confirmedpassword ? (
//       //                     <p>{formik.errors.confirmedpassword}</p>) : null}
//       //
//       //             </Form.Item>
//       //
//       //             <Form.Item>
//       //                 <RegisterButton />
//       //             </Form.Item>
//       //
//       //             <Divider className="or_divider">
//       //                 OR
//       //             </Divider>
//       //
//       //             <Form.Item>
//       //                 Already have an account?
//       //                 <Link to="/login">
//       //                     <a className="register_link" href="#">login</a>
//       //                 </Link>
//       //             </Form.Item>
//       //
//       //             {/* <Form.Item */}
//       //             {/*    className={"forgot_password_link_container"} */}
//       //             {/* > */}
//       //             {/*    <a className="forgot_password_link" href=""> */}
//       //             {/*        Forgot passw */}
//       //             {/*    </a> */}
//       //             {/* </Form.Item> */}
//       //         </form>
//       //     </Card>
//       // </div>
//       <>
//           <div id="login-custom-bg">
//               <div id="top-left-block" />
//               <div id="top-right-block" />
//               <div id="bottom-left-block" />
//               <div id="bottom-right-block" />
//           </div>
//           <div id="register-background">
//               <div id="register-form-container">
//                   <div id="register-card">
//                       <div id="register-card-title">Step 1: Register your account</div>
//                       <form onSubmit={formik.handleSubmit}>
//                           <Form.Item
//                             className="input_item_div"
//                           >
//                               <Input
//                                 id="firstName"
//                                 name="firstName"
//                                 type="text"
//                                 placeholder="Enter your first name"
//                                 onChange={formik.handleChange}
//                                 onBlur={formik.handleBlur}
//                                 value={formik.values.firstName}
//                               />
//                               {formik.touched.firstName && formik.errors.firstName ? (
//                                   <p>{formik.errors.firstName}</p>) : null}
//                           </Form.Item>
//
//                           <Form.Item
//                             className="input_item_div"
//                           >
//                               <Input
//                                 id="lastName"
//                                 name="lastName"
//                                 type="text"
//                                 placeholder="Enter your last name"
//                                 onChange={formik.handleChange}
//                                 onBlur={formik.handleBlur}
//                                 value={formik.values.lastName}
//                               />
//                               {formik.touched.lastName && formik.errors.lastName ? (
//                                   <p>{formik.errors.lastName}</p>) : null}
//                           </Form.Item>
//
//                           <Form.Item
//                             className="input_item_div"
//                           >
//                               <Input
//                                 id="username"
//                                 name="username"
//                                 type="text"
//                                 placeholder="Enter your username"
//                                 onChange={formik.handleChange}
//                                 onBlur={formik.handleBlur}
//                                 value={formik.values.username}
//                               />
//                               {formik.touched.username && formik.errors.username ? (
//                                   <p>{formik.errors.username}</p>) : null}
//                           </Form.Item>
//
//                           <Form.Item
//                             className="input_item_div"
//                           >
//                               <Input
//                                 id="email"
//                                 name="email"
//                                 type="email"
//                                 placeholder="Enter your email address"
//                                 onChange={formik.handleChange}
//                                 onBlur={formik.handleBlur}
//                                 value={formik.values.email}
//                               />
//                               {formik.touched.email && formik.errors.email ? (
//                                   <p>{formik.errors.email}</p>) : null}
//                           </Form.Item>
//
//                           <Form.Item
//                             className="input_item_div"
//                           >
//                               <Input.Password
//                                 id="password"
//                                 name="password"
//                                 type="password"
//                                 placeholder="Enter your password"
//                                 value={formik.values.password}
//                                 onChange={formik.handleChange}
//                                 onBlur={formik.handleBlur}
//                               />
//                               {formik.touched.password && formik.errors.password ? (
//                                   <p>{formik.errors.password}</p>) : null}
//                           </Form.Item>
//
//                           <Form.Item
//                             className="input_item_div"
//                           >
//                               <Input.Password
//                                 id="confirmedpassword"
//                                 name="confirmedpassword"
//                                 type="password"
//                                 placeholder="Rewrite the password"
//                                 value={formik.values.confirmedpassword}
//                                 onChange={formik.handleChange}
//                                 onBlur={formik.handleBlur}
//                               />
//                               {/* eslint-disable-next-line max-len */}
//                               {formik.touched.confirmedpassword && formik.errors.confirmedpassword ? (
//                                   <p>{formik.errors.confirmedpassword}</p>) : null}
//                           </Form.Item>
//
//                           <Form.Item>
//                               <RegisterButton />
//                           </Form.Item>
//
//                           <Divider className="or_divider">
//                               OR
//                           </Divider>
//
//                           <Form.Item>
//                               Already have an account?
//                               <Link to="/login" className="register_link">
//                                   Login!
//                               </Link>
//                           </Form.Item>
//
//                           {/* <Form.Item */}
//                           {/*    className={"forgot_password_link_container"} */}
//                           {/* > */}
//                           {/*    <a className="forgot_password_link" href=""> */}
//                           {/*        Forgot passw */}
//                           {/*    </a> */}
//                           {/* </Form.Item> */}
//                       </form>
//                   </div>
//                   <div id="register-card-svg-bg" />
//               </div>
//           </div>
//       </>
//   );
// };
//
// export default RegisterPage;
