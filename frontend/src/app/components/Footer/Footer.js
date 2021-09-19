import React from 'react';
import './Footer.css';
import { FaGithub, FaLinkedin, FaTwitter } from 'react-icons/all';

export default function Footer() {

    return (
        <>
            <div
                id={'footer'}
            >
                <div id={'footer-row-1'}>
                    <div id={'row-1-col-1'}>
                        <p className={'footer-col-title'}>Services</p>
                        <p className={'footer-col-item'}>Data Importing</p>
                        <p className={'footer-col-item'}>Data Analysis</p>
                        <p className={'footer-col-item'}>Data Visualisation</p>

                    </div>
                    <div id={'row-1-col-2'}>
                        <p className={'footer-col-title'}>About</p>
                        <p className={'footer-col-item'}>Company</p>
                        <p className={'footer-col-item'}>Team</p>
                        <p className={'footer-col-item'}>Careers</p>
                    </div>
                    <div id={'row-1-col-3'}>
                        <p className={'footer-col-title'}>Emerge</p>
                        <p style={{color: 'grey'}}>Making Data pipelines smoother.</p>
                    </div>
                </div>
                <div id={'footer-row-2'}>
                    <FaLinkedin className={'footer-social-logos'}/>
                    <FaGithub className={'footer-social-logos'}/>
                    <FaTwitter className={'footer-social-logos'}/>
                </div>
                <div id={'footer-row-3'}>Emerge © 2021</div>
            </div>
        </>
    );

}
