import cytoscape from "cytoscape/dist/cytoscape.cjs";
import React, {useEffect, useRef, useState} from 'react';

function NetworkGraphCard() {

    useEffect(() => {
        var cy = cytoscape({
            container: document.getElementById('network_graph_card_content'), // container to render in
            elements: [ // list of graph elements to start with
                { // node a
                    data: {id: 'a'}
                },
                { // node b
                    data: {id: 'b'}
                },
                { // edge ab
                    data: {id: 'ab', source: 'a', target: 'b'}
                }
            ],

            style: [ // the stylesheet for the graph
                {
                    selector: 'node',
                    style: {
                        'background-color': '#666',
                        'label': 'data(id)'
                    }
                },

                {
                    selector: 'edge',
                    style: {
                        'width': 3,
                        'line-color': '#ccc',
                        'target-arrow-color': '#ccc',
                        'target-arrow-shape': 'triangle',
                        'curve-style': 'bezier'
                    }
                }
            ],

            layout: {
                name: 'grid',
                rows: 1
            }
        });
    })

    return (
        <div></div>
    );

}

export default NetworkGraphCard;