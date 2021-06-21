import React, { Component } from 'react';
import Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';

//Line Graph
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
            y: 26.5,
          
        }, 23.3, 18.3, 13.9, 9.6]

    }, {
        name: 'Negative',
        marker: {
            symbol: 'circle'
        },
        data: [{
            y: 30,

        }, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
    }]
	
};


//Everything incoming is props, 
//Everything managed by the component itself is state.

class LineGraph extends Component {

        //define the initial state:
        state = {
            chartOptions:{
                chart: {
                    type: 'spline'
                  },
              
                  title: {
                    text: 'Line Graph Showing change Trend In Public sentiment over 12 Month Period'
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
                      data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 
                            { y: 26.5,},
                           23.3, 18.3, 13.9, 9.6]
              
                  }, {
                      name: 'Negative',
                      marker: {
                          symbol: 'circle'
                      },
                      data: [{
                          y: 30,
              
                      }, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
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
			<div class='graph' id = 'line_graph_div'>
				<HighchartsReact highcharts={Highcharts} options={this.state.chartOptions} />
			</div>
        );
    }
}

export default LineGraph;



