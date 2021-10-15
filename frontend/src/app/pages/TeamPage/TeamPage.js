/* eslint-disable */
import React from 'react';
import Switch from 'react-bootstrap/Switch';
import { Route } from 'react-router-dom';
import NavBar from '../../components/NavBar/NavBar';
import './TeamPage.css';
import ProfileCardTeamPage from '../../components/ProfileCardTeamPage/ProfileCardTeamPage';
import $ from 'jquery';

export default function TeamPage() {
    return (
        <>
            <Switch>
                <Route exact path="/team">
                    <div id={'team-page-container'}>
                        <NavBar
                            navItemColor={'black'}
                            navLogoColor={'black'}
                        />
                        <div id={'team-page-content-container'}>
                            <div className="typing-container">
                                <span id="feature-text"/>
                                <span className="input-cursor"/>
                                <span id="sentence" class="sentence">, we are team Emerge!</span>
                            </div>
                            <div className="container">
                                <ProfileCardTeamPage
                                    playerName={'Shrey Mandalia'}
                                    playerRole={'Data Parsing & Security'}
                                    linkedInUrl={'https://www.linkedin.com/in/shrey-mandalia-5b9a961b8/'}
                                    githubUrl={'https://github.com/dev-akshat'}
                                    imageUrl={'https://i.imgur.com/sX6JNYi.jpg'}
                                />
                                <ProfileCardTeamPage
                                    playerName={'Myron Maugi'}
                                    playerRole={'UI & Data Visualisation'}
                                    linkedInUrl={'https://www.linkedin.com/in/myron-lopes/'}
                                    githubUrl={'https://github.com/myronlopes-tuks'}
                                    imageUrl={'https://avatars.githubusercontent.com/u/82466821?s=400&u=1d2952eb1aa147fa79e5b76908100df546d84c98&v=4'}
                                />
                                <ProfileCardTeamPage
                                    playerName={'Wandile Makhubele'}
                                    playerRole={'Data Importing & Reporting'}
                                    linkedInUrl={'https://www.linkedin.com/in/wandile-makhubele-4a2579131'}
                                    githubUrl={'https://github.com/abDivergent'}
                                    imageUrl={'https://avatars.githubusercontent.com/u/81305595?s=400&u=3f7a2dfb37af9c23b5feac1dc47261c8508bbefa&v=4'}
                                />
                                <ProfileCardTeamPage
                                    playerName={'Rhuli Nghondzweni'}
                                    playerRole={'AI & Hosting'}
                                    linkedInUrl={'https://www.linkedin.com/in/rhuli-nghondzweni-28a0a6210/'}
                                    githubUrl={'https://github.com/u18003517-Rhuli'}
                                    imageUrl={'https://avatars.githubusercontent.com/u/82459444?s=400&u=03c9b49c05229b42983fa2a6535101cf05564bc8&v=4'}
                                />
                                <ProfileCardTeamPage
                                    playerName={'Steve Mbuguje'}
                                    playerRole={'AI'}
                                    linkedInUrl={'https://www.linkedin.com/in/steve-mbuguje-851b1520b/'}
                                    githubUrl={'https://github.com/u18008390'}
                                    imageUrl={'https://avatars.githubusercontent.com/u/73910152?s=400&u=5f3529e51f9c2f7504a574a6b0965edc5c6cbf69&v=4'}
                                />
                            </div>
                        </div>
                    </div>
                </Route>
            </Switch>
        </>
    );

}

const carouselText = [
    {
        text: 'Hi',
        color: '#E80057'
    },
    {
        text: 'Goeie more',
        color: '#E80057'
    },
    {
        text: 'Avuxeni ',
        color: '#E80057'
    },
    {
        text: 'Olá',
        color: '#E80057'
    },
    {
        text: 'Dumela',
        color: '#E80057'
    },
    {
        text: 'Sawubona',
        color: '#E80057'
    },
    {
        text: 'Mholweni',
        color: '#E80057'
    },
    {
        text: '你好',
        color: '#E80057'
    },
    {
        text: 'こんにちは',
        color: '#E80057'
    }
];

$(document)
    .ready(async function () {
        await carousel(carouselText, '#feature-text');
    });

async function typeSentence(sentence, eleRef, delay = 100) {
    const letters = sentence.split('');
    let i = 0;
    while (i < letters.length) {
        await waitForMs(delay);
        $(eleRef)
            .append(letters[i]);
        i++;
    }
}

async function deleteSentence(eleRef) {
    const sentence = $(eleRef)
        .html();
    if (typeof sentence !== 'undefined') {
        const letters = sentence.split('');
        let i = 0;
        while (letters.length > 0) {
            await waitForMs(100);
            letters.pop();
            $(eleRef)
                .html(letters.join(''));
        }
    }
}

async function carousel(carouselList, eleRef) {
    var i = 0;
    while (true) {
        updateFontColor(eleRef, carouselList[i].color);
        await typeSentence(carouselList[i].text, eleRef);
        await waitForMs(1500);
        await deleteSentence(eleRef);
        await waitForMs(500);
        i++;
        if (i >= carouselList.length) {
            i = 0;
        }
    }
}

function updateFontColor(eleRef, color) {
    $(eleRef)
        .css('color', color);
}

function waitForMs(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
