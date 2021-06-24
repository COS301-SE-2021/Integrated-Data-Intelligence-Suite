import React, { Component } from 'react';

class SearchBar extends Component{

	render(){

		return(
	
				<div class='graph' id='search_bar_div'>
					<form
						onSubmit={e => {
						e.preventDefault();
						}}
						method = 'get'
					>
						<label htmlFor="header-search">
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