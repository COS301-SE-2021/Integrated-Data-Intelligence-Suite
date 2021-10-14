import React from 'react';
import './NavBar.css';
import { useHistory } from 'react-router-dom';

export default function NavBar(props) {
    const history = useHistory();

    const handleTeamPageClick = () => {
        // console.log('team page clicked');
        history.push('/team');
    };

    const goToTeamGithubPage = () => {
        window.open('https://github.com/COS301-SE-2021/Integrated-Data-Intelligence-Suite', '_blank');
    };

    const goToLoginPage = () => {
        history.push('/login');
    };

    const goToHomePage = () => {
        history.push('/');
    };

    return (
        <>
            <div
              id="nav-bar"
            >
                <div id="nav-bar-left-col">
                    <div
                      id="nav-bar-logo"
                      className={`nav-logo-color-${props.navLogoColor}`}
                      onClick={() => {
                            goToHomePage();
                        }}
                    />
                </div>
                <div id="nav-bar-right-col">
                    <div
                      className={`nav-bar-item ${props.navItemColor}`}
                      onClick={() => {
                            handleTeamPageClick();
                        }}
                    >
                        Team
                    </div>
                    <div
                      className={`nav-bar-item ${props.navItemColor}`}
                      onClick={() => {
                            goToTeamGithubPage();
                        }}
                    >
                        Github
                    </div>
                </div>
                <button
                  id="nav-bar-sign-in"
                  onClick={()=>{
                      goToLoginPage();
                  }}
                >
                    <span id="nav-bar-sign-in-text">
                        Sign in
                    </span>
                </button>
            </div>
        </>
    );
}
