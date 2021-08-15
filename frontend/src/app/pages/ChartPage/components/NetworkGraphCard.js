import cytoscape from "cytoscape/dist/cytoscape.cjs";
import React, {useEffect, useRef, useState} from 'react';
import timelineNodes from "../resources/networkGraphNodes.json";
import "../styles/NetworkGraph.css";
import network_stylesheet from "../resources/networkGraphNodesStyleSheet.json";

function NetworkGraphCard() {

    useEffect(() => {
        var cy = cytoscape({
            container: document.getElementById('network_graph_card_content'), // container to render in
            // elements: [ // list of graph elements to start with
            //     { // node a
            //         data: {id: 'a'}
            //     },
            //     { // node b
            //         data: {id: 'b'}
            //     },
            //     { // edge ab
            //         data: {id: 'ab', source: 'a', target: 'b'}
            //     }
            // ],

            // style: [ // the stylesheet for the graph
            //     {
            //         selector: 'node',
            //         style: {
            //             'background-color': 'red',
            //             'label': 'data(id)'
            //         }
            //     },
            //
            //     {
            //         selector: 'edge',
            //         style: {
            //             'width': 3,
            //             'line-color': '#ccc',
            //             'target-arrow-color': '#ccc',
            //             'target-arrow-shape': 'triangle',
            //             'curve-style': 'bezier'
            //         }
            //     }
            // ],

            layout: {
                name: 'random',
                rows: 1
            }
        });

        //Adding nodes to the layout
        cy.add(timelineNodes);

        //Adding a stylesheet to the network graph
        cy.style(network_stylesheet);
    })

    // return (
    //     <div></div>
    // );

    return null; // not rendering anything
}

export default NetworkGraphCard;