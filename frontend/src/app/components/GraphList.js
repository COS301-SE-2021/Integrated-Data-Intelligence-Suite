// import React from 'react';

// import NetworkGraph from './components/NetworkGraph';
// import MapGraph from './components/MapGraph';
// import TimelineGraph from './components/TimelineGraph';

import SearchBar from './SearchBar';
import LineGraph from './LineGraph';

import { Alert, Button, Badge } from "react-bootstrap";
import React, { useState, useEffect } from 'react';

let linegraph_options = {
	chart: {
	  type: 'spline'
	},

	title: {
	  text: 'Timeline Showing change in Public sentiment over 12 Month Period'
	},

	xAxis: {
		categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
					'Sep', 'Oct', 'Nov', 'Dec' ]
	},

	yAxis: {
		title:{
			text: 'Number of Tweets'
		},
		labels: {
			formatter: function(){
				return this.value + 'xxxx'; 
			}
		}
	},
	
	tooltip: {
        crosshairs: true,
        shared: true
    },


    plotOptions: {
        spline: {
            marker: {
                radius: 3,
                lineColor: '#666666',
                lineWidth: 1
            }
        }
    },


	series: [{
        name: 'Positive',
        marker: {
            symbol: 'square'
        },
        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, {
            y: 0,
          
        }, 23.3, 18.3, 13.9, 9.6]

    }, {
        name: 'Negative',
        marker: {
            symbol: 'circle'
        },
        data: [{
            y: 0,

        }, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
    }]
	
};


class GraphList extends React.Component {
	constructor(props){
		super(props);
		this.lineGraphElement = React.createRef();
	}

	updateLineGraph = () =>{
		this.lineGraphElement.current.changeChartOptions(linegraph_options);
	}


	render(){
		return (
	
			<div>

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
							name="" 
						/>
						<button 
						type="submit"
						onClick={this.updateLineGraph}
						>Search</button>
					</form>
				</div>

				

				<LineGraph ref = {this.lineGraphElement}/>

				<button onClick={this.updateLineGraph}>ME</button>

	
	
				
	
			</div>
	
	
	
		);

	}

}


export default GraphList;



