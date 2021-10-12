import React, { useState } from 'react';
import LoginCard from '../../components/LoginCard/LoginCard';
import RegisterCard from '../../components/RegisterCard';

// Validation Function
export default function LoginPage() {
    const [showLoginForm, setShowLoginForm] = useState(true);

    function animate(initialAnimation, cb, event) {
        cb(!initialAnimation);
        const button = document.getElementById('login-card-svg-bg');
        const loginForm = document.getElementById('login-card');
        const registerForm = document.getElementById('register-card');
        if (!initialAnimation) {
            loginForm.classList.remove('fadeOut');
            registerForm.classList.remove('fadeIn');
            button.classList.remove('slideRight');

            button.classList.add('slideLeft');
            loginForm.classList.add('fadeIn');
            registerForm.classList.add('fadeOut');
            button.children[0].innerHTML = 'Register';
            // console.log(registerForm);
        } else {
            loginForm.classList.remove('fadeIn');
            registerForm.classList.remove('fadeOut');
            registerForm.classList.remove('disabled');
            button.classList.remove('slideLeft');

            button.classList.add('slideRight');
            loginForm.classList.add('fadeOut');
            registerForm.classList.add('fadeIn');
            button.children[0].innerHTML = 'Login';

            // console.log(registerForm);
        }
    }

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
            <div id="login-background" className="login-page">
                <div id="login-form-container">

                    <div>
                        <div>
                            <LoginCard />
                            <RegisterCard />
                        </div>
                    </div>
                    <div id="login-card-svg-bg">
                        <button className="clickable" onClick={(event) =>animate(showLoginForm, setShowLoginForm, event)}>Register</button>
                    </div>
                </div>
            </div>
        </>
    );
}
