import React from 'react';
import './NavBar.css';

export default class NavBar extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <div
                    id={'nav-bar'}
                >
                    <div id={'nav-bar-left-col'}>
                        <div id={'nav-bar-logo'}/>
                    </div>
                    <div id={'nav-bar-right-col'}>
                        <div className={'nav-bar-item'}>About</div>
                        <div className={'nav-bar-item'}>Features</div>
                        <div className={'nav-bar-item'}>Blog</div>
                    </div>
                </div>
            </>
        );
    }
}
