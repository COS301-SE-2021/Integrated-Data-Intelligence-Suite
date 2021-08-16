import React, {Component} from 'react';
import {Input} from 'antd';
import "./SearchBar.css";

const {Search} = Input;

class SearchBar extends Component {

    // showLoadingIcon = false;
    constructor() {
        super();
        this.state = {showLoadingIcon: false};
        this.onSearch = this.onSearch.bind(this);
    }

    //Runs when the search button is clicked
    onSearch(values) {
        alert(values + "= Search term");
        //Show loading icon while API request is waiting for data
        this.setState((prevState) => ({showLoadingIcon: true}))
        const requestOptions = {
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
        };
        const url = '/main/' + values;
        fetch(url, requestOptions)
            .then(response => {
                return response.json()
            }).then(json => {
            //remove or stop the loading icon
            this.setState((prevState) => ({showLoadingIcon: false}))
            
            //JSON response from API
        });

    }

    render() {
        return (
            <Search
                placeholder="looking for something?"
                onSearch={this.onSearch}
                // style={{ width: 200 }}
                loading={this.state.showLoadingIcon}
            />
        );
    }
}


export default SearchBar; 