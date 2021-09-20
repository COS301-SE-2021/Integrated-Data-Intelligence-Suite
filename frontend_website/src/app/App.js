import React, { Component } from 'react';
import { HashRouter as Router, Link, Route, Switch } from "react-router-dom"
import HomePage from './pages/HomePage/HomePage';
import './App.scss';


class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route exact path="/">
                        <HomePage/>
                    </Route>

                    <Route path="*">
                        <div>
                            <h2>Page not Found</h2>
                            <Link to="/"> back to home page</Link>
                        </div>
                    </Route>
                </Switch>
            </Router>
        );
    }
}

export default App;
