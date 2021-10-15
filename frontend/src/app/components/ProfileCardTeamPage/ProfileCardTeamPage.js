import React from 'react';
import './ProfileCardTeamPage.css';
import { AiFillGithub, AiFillLinkedin } from 'react-icons/all';

export default function ProfileCardTeamPage(props) {

    function handleLinkedIn(url) {
        window.open(url, '_blank');
    }

    function goToGithubPage(url) {
        window.open(url, '_blank');
    }

    return (
        <>
            <div className={'profile-card-container'}>
                <img
                    src={props.imageUrl}
                    alt=""
                    className={'profile-card-image'}
                />
                <span className={'profile-card-name'}>
                    {props.playerName}
                </span>
                <span className={'profile-card-role'}>
                    {props.playerRole}
                </span>
                <span
                    className={'profile-card-linkedin'}
                    onClick={() => {
                        handleLinkedIn(props.linkedInUrl);
                    }}
                >
                    <AiFillLinkedin className={'profile-card-linkedin-logo'}/>
                    LinkedIn
                </span>
                <span
                    className={'profile-card-github'}
                    onClick={() => {
                        goToGithubPage(props.githubUrl);
                    }}
                >
                    <AiFillGithub className={'profile-card-linkedin-logo'}/>
                    Github
                </span>
            </div>
        </>
    );
}
