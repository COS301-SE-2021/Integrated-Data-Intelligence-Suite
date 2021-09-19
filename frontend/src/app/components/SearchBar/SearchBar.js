import React, { Component } from 'react';
import { Input } from 'antd';
import './SearchBar.css';
import template_json from '../../Mocks/messageMock.json';

const { Search } = Input;

function getLocalUser() {
    const localUser = localStorage.getItem('user');
    if (localUser) {
        // console.log("user logged in is ", localUser)
        return JSON.parse(localUser);
    }
    return null;
}

class SearchBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = { showLoadingIcon: false };
        this.onSearch = this.onSearch.bind(this);
        this.handleTextChange = this.handleTextChange.bind(this);
        this.state.user = getLocalUser();
    }

    handleTextChange(some_json_data) {
        this.props.handleTextChange(some_json_data);
    }

    // Runs when the search button is clicked
    onSearch(values) {
        //this.handleTextChange(template_json);

        // Show loading icon while API request is waiting for data
        this.setState((prevState) => ({ showLoadingIcon: true }));
        const obj = {
            permission: this.state.user.permission,
            username: this.state.user.username,
        };
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(obj),
        };
        const url = `/main/${values}`;
        // console.log(requestOptions)
        fetch(url, requestOptions)
            .then((response) => response.json())
            .then((json) => {
                this.setState((prevState) => ({ showLoadingIcon: false }));
                // remove or stop the loading icon
                this.handleTextChange(json);
            })
            .catch((err) => {
                this.setState((prevState) => ({ showLoadingIcon: false }));
                console.log('error while retrieving data from backend');
                console.log(err.message);
            });
    }

    render() {
        return (
            <Search
                placeholder="Search for a keyword?"
                onSearch={this.onSearch}
                loading={this.state.showLoadingIcon}
            />
        );
    }
}

export default SearchBar;
