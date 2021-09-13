import React from 'react';
import { Route, Switch } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import './CreditsPage.css';

export default class CreditsPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <Switch>
                    <Route exact path="/credits">
                        <div id={'cred-page-container'}>
                            <SideBar currentPage={'4'}/>
                            <div id={'cred-content-section'}>
                                content section goes here
                            </div>
                        </div>

                    </Route>
                </Switch>
            </>
        );
    }
}



