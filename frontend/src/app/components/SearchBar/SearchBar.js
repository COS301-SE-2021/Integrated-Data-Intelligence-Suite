import React, {Component} from 'react';
import {Input} from 'antd';
import "./SearchBar.css";
import template_json from "../../pages/ChartPage/resources/graphStructures/message.json"

const {Search} = Input;

class SearchBar extends Component {

    // showLoadingIcon = false;
    constructor(props) {
        super(props);
        this.state = {showLoadingIcon: false};
        this.onSearch = this.onSearch.bind(this);
        this.handleTextChange = this.handleTextChange.bind(this);
    }

    handleTextChange(some_json_data) {
        this.props.handleTextChange(some_json_data);
    }

    //Runs when the search button is clicked
    onSearch(values) {
        alert(values + "= Search term");

        // this.handleTextChange(template_json)

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
            this.handleTextChange(json);
            this.setState((prevState) => ({showLoadingIcon: false}))

            //JSON response from API
        });

    }

    render() {
        return (
            <Search
                placeholder="looking for something?"
                onSearch={this.onSearch}
                loading={this.state.showLoadingIcon}
            />
        );
    }
}


export default SearchBar; 