import React, { Component } from 'react';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { Alert, Button, Badge } from "react-bootstrap";
import Moment from "react-moment";

class SearchBar extends Component{

	render(){
		// let { tweets } = state;
		return(
	
				<div id='search_bar_div'>
					<form
						onSubmit={e => {
						e.preventDefault();
						}}
						method = 'get'
					>
						<label htmlFor="header-search">
							<span className="visually-hidden">Search blog posts</span>
						</label>
						<input
							type="text"
							id="header-search"
							placeholder="Enter Search keyword"
							name="s" 
						/>
						<button type="submit">Search</button>
					</form>
				</div>
		);
	}
}


export default SearchBar; 