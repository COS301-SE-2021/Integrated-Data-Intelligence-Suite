import React, { Component } from 'react';
import Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';

require('highcharts/modules/networkgraph')(Highcharts);
require('highcharts/modules/exporting')(Highcharts);

//Network Graph
// Highcharts.addEvent(
//     Highcharts.Series,
//     'afterSetOptions',
//     function (e) {
//         var colors = Highcharts.getOptions().colors,
//             i = 0,
//             nodes = {};

//         if (
//             this instanceof Highcharts.seriesTypes.networkgraph &&
//             e.options.id === 'lang-tree'
//         ) {
//             e.options.data.forEach(function (link) {

//                 if (link[0] === 'Proto Indo-European') {
//                     nodes['Proto Indo-European'] = {
//                         id: 'Proto Indo-European',
//                         marker: {
//                             radius: 20
//                         }
//                     };
//                     nodes[link[1]] = {
//                         id: link[1],
//                         marker: {
//                             radius: 10
//                         },
//                         color: colors[i++]
//                     };
//                 } else if (nodes[link[0]] && nodes[link[0]].color) {
//                     nodes[link[1]] = {
//                         id: link[1],
//                         color: nodes[link[0]].color
//                     };
//                 }
//             });

//             e.options.nodes = Object.keys(nodes).map(function (id) {
//                 return nodes[id];
//             });
//         }
//     }
// );

let network_graph_options = {
	chart: {
        type: 'networkgraph',
        height: '100%'
    },
    title: {
        text: 'Force-Directed Network Graph between Relationships in Tweets '
    },
    subtitle: {
        text: ''
    },
    plotOptions: {
        networkgraph: {
            keys: ['from', 'to'],
            layoutAlgorithm: {
                enableSimulation: true,
                friction: -0.9
            }
        }
    },
    series: [{
        dataLabels: {
            enabled: true,
            linkFormat: ''
        },
        id: 'lang-tree',
        data: [
            ['Proto Indo-European', 'Balto-Slavic'],
            ['Proto Indo-European', 'Anatolian'],
            ['Latino-Faliscan', 'Latin'],

            // Leaves:
            ['Proto Indo-European', 'Phrygian'],
            ['Anatolian', 'Luwic'],
            ['Anatolian', 'Lydian'],   
        ]
    }]
};

class NetworkGraph extends Component {
    //Define the initial state of the Network Graph
    state = {
        chartOptions:{
            chart: {
                type: 'networkgraph',
                height: '100%'
            },

            title: {
                text: 'Relationships Between Consequents and Antecedent in Tweets '
            },

            subtitle: {
                text: 'A Force-Directed Network Graph'
            },
            
            plotOptions: {
                networkgraph: {
                    keys: ['from', 'to'],
                    layoutAlgorithm: {
                        enableSimulation: true,
                        friction: -0.9
                    }
                }
            },
            series: [{
                dataLabels: {
                    enabled: true,
                    linkFormat: ''
                },
                id: 'lang-tree',
                data: [
                    ['Proto Indo-European', 'Balto-Slavic'],
                    ['Proto Indo-European', 'Anatolian'],
                    ['Latino-Faliscan', 'Latin'],
        
                    // Leaves:
                    ['Proto Indo-European', 'Phrygian'],
                    ['Anatolian', 'Luwic'],
                    ['Anatolian', 'Lydian'],   
                ]
            }]
        }
    };
    

    changeChartOptions = (newChartOption) => {
        this.setState({
            chartOptions: newChartOption
        });
    }


    render () {
        return (
			<div class='graph' id = 'network_graph_div'>
				<HighchartsReact highcharts={Highcharts} options={this.state.chartOptions} />
			</div>
        );
    }

}

export default NetworkGraph;


