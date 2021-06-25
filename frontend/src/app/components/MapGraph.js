import React, { Component } from 'react';

import Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';
import mapDataSouthAfrica from './mapDataSouthAfrica';

require('highcharts/modules/map')(Highcharts);


let south_africa_data = [
    ['za-ec', 0],
    ['za-np', 1],
    ['za-nl', 2],
    ['za-wc', 3],
    ['za-nc', 4],
    ['za-nw', 5],
    ['za-fs', 6],
    ['za-gt', 7],
    ['za-mp', 8]
];


let mapgraph_sa_options = {
	chart: {
        map: 'countries/za/za-all'
    },

    title: {
        text: 'Source Map showing Sentiment over each province'
    },

    subtitle: {
        text: 'Source map: <a href="http://code.highcharts.com/mapdata/countries/za/za-all.js">South Africa</a>'
    },

    mapNavigation: {
        enabled: true,
        buttonOptions: {
            verticalAlign: 'bottom'
        }
    },

    colorAxis: {
        min: 0
    },

    series: [{
		mapData: mapDataSouthAfrica,
        data: south_africa_data,
        name: 'Sentiment',
        states: {
            hover: {
                color: '#BADA55'
            }
        },
        dataLabels: {
            enabled: true,
            format: '{point.name}'
        }
    }]
};

class MapGraph extends React.Component{
    //Define the initial State
    state = {
        chartOptions:{
            chart: {
                map: 'countries/za/za-all'
            },
        
            title: {
                text: 'Source Map showing Sentiment over each province'
            },
        
            subtitle: {
                text: 'Source map: <a href="http://code.highcharts.com/mapdata/countries/za/za-all.js">South Africa</a>'
            },
        
            mapNavigation: {
                enabled: true,
                buttonOptions: {
                    verticalAlign: 'bottom'
                }
            },
        
            colorAxis: {
                min: 0
            },
        
            series: [{
                mapData: mapDataSouthAfrica,
                data: south_africa_data,
                name: 'Sentiment',
                states: {
                    hover: {
                        color: '#BADA55'
                    }
                },
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }]
        }
    };

    //
    changeChartOptions = (newChartOption) => {
        this.setState({
            chartOptions: newChartOption
        });
    }



	render(){
		return (
			<div class='graph' id='map_div'>
				{/*<h2>Map</h2>*/}
				<HighchartsReact
                    options={this.state.chartOptions}
                    constructorType={'mapChart'}
                    highcharts={Highcharts}
				/>
			</div>
		);
	}
}


export default MapGraph;
