// import React from 'react';

// import NetworkGraph from './components/NetworkGraph';
// import MapGraph from './components/MapGraph';
// import TimelineGraph from './components/TimelineGraph';


import React, { useState, useEffect } from 'react';
import LineGraph from './LineGraph';
import NetworkGraph from './NetworkGraph';
import TimelineGraph from './TimelineGraph';



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
        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, {y: 0,}, 23.3, 18.3, 13.9, 9.6]

    }, {
        name: 'Negative',
        marker: {
            symbol: 'circle'
        },
        data: [{y: 0,}, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
    }]
	
};

let network_graph_options = {
	chart: {
        type: 'networkgraph'
    },

    plotOptions: {
        networkgraph: {
            layoutAlgorithm: {
                enableSimulation: true
            }
        }
    },

    series: [{
        link: {
            width: 5
        },
        dataLabels: {
            enabled: true
        },
        data: [{
            from: 'Europe',
            to: 'UK'
        }, {
            from: 'Europe',
            to: 'Poland',
            color: 'red',
            width: 10,
            /* dashStyle: 'dot' */
        }, {
            from: 'Europe',
            to: 'Italy'
        }, {
            from: 'UK',
            to: 'London'
        }, {
            from: 'UK',
            to: 'Bristol'
        }, {
            from: 'London',
            to: 'London Centre'
        }, {
            from: 'Poland',
            to: 'Warsaw'
        }, {
            from: 'Poland',
            to: 'Krakow',
            color: 'green'
        }, {
            from: 'Italy',
            to: 'Roma'
        }, {
            from: 'Italy',
            to: 'Piza'
        }],
        nodes: [{
            id: 'Krakow',
            color: 'yellow'
        }, {
            id: 'Italy',
            color: 'pink'
        }]
    }]


};

let timeline_graph_options = {
	chart: {
        type: 'timeline'
    },
    accessibility: {
        screenReaderSection: {
            beforeChartFormat: '<h5>{chartTitle}</h5>' +
                '<div>{typeDescription}</div>' +
                '<div>{chartSubtitle}</div>' +
                '<div>{chartLongdesc}</div>' +
                '<div>{viewTableButton}</div>'
        },
        point: {
            valueDescriptionFormat: '{index}. {point.label}. {point.description}.'
        }
    },
    xAxis: {
        visible: false
    },

    yAxis: {
        visible: false
    },

    title: {
        text: 'Timeline of Space Exploration'
    },

    subtitle: {
        text: 'Info source: <a href="https://en.wikipedia.org/wiki/Timeline_of_space_exploration">www.wikipedia.org</a>'
    },

    colors: [
        '#4185F3',
        '#427CDD',
        '#406AB2',
        '#3E5A8E',
        '#3B4A68',
        '#363C46'
    ],
    series: [{
        data: [{
            name: 'First dogs',
            label: '1999: First dogs in space',
            description: '22 July 1951 First dogs in space (Dezik and Tsygan) '
        }, {
            name: 'Sputnik 1',
            label: '1957: First artificial satellite',
            description: '4 October 1957 First artificial satellite. First signals from space.'
        }, {
            name: 'First human spaceflight',
            label: '1961: First human spaceflight (Yuri Gagarin)',
            description: 'First human spaceflight (Yuri Gagarin), and the first human-crewed orbital flight'
        }, {
            name: 'First human on the Moon',
            label: '1969: First human on the Moon',
            description: 'First human on the Moon, and first space launch from a celestial body other than the Earth. First sample return from the Moon'
        }, {
            name: 'First space station',
            label: '1971: First space station',
            description: 'Salyut 1 was the first space station of any kind, launched into low Earth orbit by the Soviet Union on April 19, 1971.'
        }, {
            name: 'Apollo–Soyuz Test Project',
            label: '1975: First multinational manned mission',
            description: 'The mission included both joint and separate scientific experiments, and provided useful engineering experience for future joint US–Russian space flights, such as the Shuttle–Mir Program and the International Space Station.'
        }]
    }]

};


class GraphList extends React.Component {
	constructor(props){
		super(props);
		this.lineGraphElement = React.createRef();
		this.networkGraphElement = React.createRef();
		this.timelineGraphElement = React.createRef();
	}

	updateAllGraphs = (/*Data Received from Backend*/) =>{
		this.updateLineGraph(/*Data Received from Backend*/);

		this.updateNetworkGraph(/*Data Received from Backend*/);

		this.updateTimelineGraph(/*Data Received from Backend*/);
	}

	updateLineGraph = () => {

		//Process Data Received from Backend

		this.lineGraphElement.current.changeChartOptions(linegraph_options);
	}

	updateNetworkGraph = () =>{
		//process Data Received from Backend

		this.networkGraphElement.current.changeChartOptions(network_graph_options);
	}

	updateTimelineGraph = () =>{
		//process Data Received from Backend

		this.timelineGraphElement.current.changeChartOptions(timeline_graph_options);
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
						id="search_btn"
						type="submit"
						onClick={this.updateAllGraphs}
						>Search</button>
					</form>
				</div>

				<LineGraph ref = {this.lineGraphElement}/>

				<NetworkGraph ref = {this.networkGraphElement}/>

				<TimelineGraph ref = {this.timelineGraphElement}/>

				








			</div>
	
	
	
		);

	}

}


export default GraphList;



