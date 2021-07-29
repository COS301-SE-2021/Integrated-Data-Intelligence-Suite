import React, {useState, useEffect} from 'react';
import LineGraph from './LineGraph';
import NetworkGraph from './NetworkGraph';
import TimelineGraph from './TimelineGraph';
import MapGraph from './MapGraph';
import mapDataSouthAfrica from './mapDataSouthAfrica';
import {data} from 'browserslist';


let linegraph_options = {
    chart: {
        type: 'spline'
    },

    title: {
        text: 'Timeline Showing Trend in Public sentiment over 12 Month Period'
    },

    xAxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
            'Sep', 'Oct', 'Nov', 'Dec']
    },

    yAxis: {
        title: {
            text: 'Number of Tweets'
        },
        labels: {
            formatter: function () {
                return this.value + '';
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


        // nodes: [{
        //     id: 'Krakow',
        //     color: 'yellow'
        // }, {
        //     id: 'Italy',
        //     color: 'pink'
        // }]
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
        text: 'Timeline of patterns found on each day'
    },

    subtitle: {
        text: 'Capped over a 7 Day period'
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
        dataLabels: {
            allowOverlap: false,
            format: '<span style="color:{point.color}">● </span><span style="font-weight: bold;" > ' +
                '{point.x:%d %b %Y}</span><br/>{point.label}'
        },
        marker: {
            symbol: 'circle'
        },
        data: [{
            x: Date.UTC(2021, 11, 22),
            name: 'Name 1',
            label: 'Label 1',
            description: "description 1"
        }, {
            x: Date.UTC(2021, 10, 4),
            name: 'Name 2',
            label: 'Label 2',
            description: "Description 2"
        }, {
            x: Date.UTC(2021, 1, 4),
            name: 'Name 3',
            label: 'Label 3',
            description: "orbit."
        }, {
            x: Date.UTC(2021, 2, 12),
            name: 'Name 4',
            label: 'Label 4',
            description: "r sp61."
        }, {
            x: Date.UTC(2021, 1, 3),
            name: 'Name 5',
            label: 'First soft landing on the Moon',
            description: "Yuri Gahie Earth on 12 April 1961."
        }, {
            x: Date.UTC(2021, 7, 20),
            name: 'First human on the Moon',
            label: 'First human on the Moon',
            description: "Apollo modully 20, 1969, at 20:17 UTC."
        }, {
            x: Date.UTC(2021, 9, 19),
            name: 'First space station',
            label: 'First space station',
            description: "stations."
        }, {
            x: Date.UTC(2021, 11, 2),
            name: 'First soft Mars landing',
            label: 'First soft Mars landing',
            description: "Marn 1960 s an attached lander."
        }, {
            x: Date.UTC(2021, 3, 17),
            name: 'Closest flyby of the Sun',
            label: 'Closest flyby of the Sun',
            description: "Helios-A  into heliWest Air Force Station, Florida."
        }, {
            x: Date.UTC(2021, 11, 4),
            name: 'First orbital exploration of Venus',
            label: 'First orbital exploration of Venus',
            description: "Thharat data until October 1992."
        }, {
            x: Date.UTC(2021, 1, 19),
            name: 'First inhabited space station',
            label: 'First inhabited space station',
            description: "was r bad a greaspacecraft."
        }, {
            x: Date.UTC(2021, 7, 8),
            name: 'First astrometric satellite',
            label: 'First astrometric satellite',
            description: "Hiptilthe sky."
        }, {
            x: Date.UTC(2020, 10, 20),
            name: 'First multinational space station',
            label: 'First multinational space station',
            description: " 0.[7] It has been inhabited continuously since that date."
        }]
    }]

};


function getData(userKeyword) {
    console.log('inside here');


    /******************RHULI CHANGES HERE**************/

    let httpRequest = new XMLHttpRequest();

    httpRequest.onreadystatechange = function () { // supports older browsers //TODO: But we can use a wrapper i.e jquery, to solve any potential future issues;
        if (this.readyState === 4 && this.status === 200) {
            alert(JSON.parse(this.responseText));
            console.log(JSON.parse(this.responseText));
            //TODO: we can prosses here when things are loaded
        }
    };
    httpRequest.open("GET", "http://localhost:9000/main/" + userKeyword);
    httpRequest.send();


    /******************RHULI CHANGES HERE**************/

        //Send Request to server
    let requestUrl = 'http://localhost:9000/main/' + userKeyword;  //
    let eventSource = undefined;

    try {

        eventSource = new EventSource(requestUrl);
    } catch (error) {
        console.log("Error found");
    }


    eventSource.onopen = (event) => {
        console.log("connection opened");
    }

    //Data received from Server
    eventSource.onmessage = (event) => {
        console.log("result", event.data);
        return data;
    }

    //Data
    eventSource.onerror = (event) => {
        console.log(event.target.readyState)
        if (event.target.readyState === EventSource.CLOSED) {
            console.log('eventsource closed (' + event.target.readyState + ')')
        }

        //close the connection
        eventSource.close();
    }

    return () => {
        eventSource.close();
        console.log("eventsource closed")
    }
}

class GraphList extends React.Component {
    constructor(props) {
        super(props);
        this.lineGraphElement = React.createRef();
        this.networkGraphElement = React.createRef();
        this.timelineGraphElement = React.createRef();

        this.mapGraphElement = React.createRef();
    }


    updateAllGraphs = (/*keyword*/) => {
        let userKeyword = document.getElementById('header-search').value;
        // console.log("==============================");
        // console.log("Keyword:" + document.getElementById('header-search').value);
        // console.log("==============================");

        let data_from_backend = getData(userKeyword);


        this.updateLineGraph(/*Data Received from Backend*/);

        this.updateNetworkGraph(/*Data Received from Backend*/);

        this.updateTimelineGraph(/*Data Received from Backend*/);

        this.updateMapGraph(/*Data Received from Backend */)
    }

    updateLineGraph = () => {

        //Process Data Received from Backend
        //Replace with Data
        linegraph_options.series = [{
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
        }, {
            name: 'Neutral',
            marker: {
                symbol: 'circle'
            },
            data: [{y: 27,}, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }];

        this.lineGraphElement.current.changeChartOptions(linegraph_options);
    }

    updateNetworkGraph = () => {
        //process Data Received from Backend

        //Data from backend server
        network_graph_options.series[0].data = [{
            from: 'consequent',
            to: 'antecedent'
        }, {
            from: 'consequent',
            to: 'something else',
            color: 'red',

            /* dashStyle: 'dot' */
        }];

        this.networkGraphElement.current.changeChartOptions(network_graph_options);
    }

    updateTimelineGraph = (/**Data received from backend */) => {
        timeline_graph_options.series[0].data = [{
            x: Date.UTC(2021, 11, 22),
            name: 'Name 1',
            label: 'Label 1',
            description: "description 1"
        }, {
            x: Date.UTC(2021, 10, 4),
            name: 'Name 2',
            label: 'Label 2',
            description: "Description 2"
        }];

        //process Data Received from Backend
        this.timelineGraphElement.current.changeChartOptions(timeline_graph_options);
    }

    updateMapGraph = (/**Data Received from backend */) => {
        //Extract Map data from data_received_from_backend

        let map_graph_options = {
            chart: {
                map: 'countries/za/za-all'
            },

            title: {
                text: 'Map showing General Sentiment in each province'
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

                data: [
                    ['za-ec', 8],
                    ['za-np', 8],
                    ['za-nl', 8],
                    ['za-wc', 8],
                    ['za-nc', 8],
                    ['za-nw', 8],
                    ['za-fs', 8],
                    ['za-gt', 1],
                    ['za-mp', 8]
                ],

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

        console.log(map_graph_options.series[0]);// Returns the entire array
        console.log(map_graph_options.series[0].data); //returns an array

        //How to Assign Data Received from Rhuli
        map_graph_options.series[0].data = [
            ["za-ec", 2], ["za-np", 2], ["za-nl", 2], ["za-wc", 2], ["za-nc", 2], ["za-nw", 2], ["za-fs", 2], ["za-gt", 2], ["za-mp", 2]]
        ;

        //map_graph_options.series[0].data = data_from_backend.mapData;


        //Json Structure for Backend
        //Json String Version:[["za-ec",2],["za-np",2],["za-nl",2],["za-wc",2],["za-nc",2],["za-nw",2],["za-fs",2],["za-gt",2],["za-mp",2]]
        //Gets converted to JSON OBJ


        this.mapGraphElement.current.changeChartOptions(map_graph_options);

    }

    render() {
        return (
            <div class='graph_list' id="graph_list_div">

                <div class='search_bar' id='search_bar_div'>
                    <form
                        className='search-form'
                        id='input_keyword_form'
                        name="form-keyword"
                        onSubmit={e => {
                            e.preventDefault();
                        }}
                        method='get'
                    >
                        <label htmlFor="header-search">
                            {/*<span className="visually-hidden">Search blog posts</span>*/}
                        </label>
                        <input
                            type="text"
                            id="header-search"
                            placeholder="Enter Search keyword"
                            name="input-keyword"
                        />
                        <button
                            id="search_btn"
                            type="submit"
                            onClick={this.updateAllGraphs}
                        >Search
                        </button>
                    </form>
                </div>

                <LineGraph ref={this.lineGraphElement}/>

                <NetworkGraph ref={this.networkGraphElement}/>

                <TimelineGraph ref={this.timelineGraphElement}/>

                <MapGraph ref={this.mapGraphElement}/>

            </div>

        );

    }

}


export default GraphList;



