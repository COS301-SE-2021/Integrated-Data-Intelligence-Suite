import React, {useEffect, useRef, useState} from 'react';
import cytoscape from "cytoscape/dist/cytoscape.cjs";
import fcose from "cytoscape-fcose";
import cosebase from "cose-base";
import timelineNodes from "../resources/graphStructures/networkGraphNodes.json";
import "../styles/NetworkGraph.css";
import network_stylesheet from "../resources/graphStructures/networkGraphNodesStyleSheet.json";
import layoutUtilities from "cytoscape-layout-utilities";
import ScriptTag from 'react-script-tag';

const Demo = props => (
    <ScriptTag type="text/javascript" src="https://unpkg.com/cytoscape-layout-utilities/cytoscape-layout-utilities.js"/>
)
cytoscape.use(fcose);

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

                ready: function () {
                    // let layoutUtilities = this.layout{
                    //     desiredAspectRatio: this.width() / this.height()
                    // });

                    this.nodes().forEach(function (node) {
                        let size = Math.random() * 120 + 30;
                        node.css("width", size);
                        node.css("height", size);
                    });
                    this.layout({name: 'fcose'}).run();

                    const layout = this.layout({
                        name: 'fcose',
                        incremental: false,
                        quality: "default",
                        randomize: false,
                        animate: true,
                        animationEasing: 'ease-out',
                        uniformNodeDimensions: false,
                        packComponents: true,
                        tile: true,
                        nodeRepulsion: 4500,
                        idealEdgeLength: 50,
                        edgeElasticity: 0.45,
                        nestingFactor: 0.1,
                        gravity: 0.25,
                        gravityRange: 3.8,
                        gravityCompound: 1.0,
                        gravityRangeCompound: 1.5,
                        numIter: 2500,
                        tilingPaddingVertical: 10,
                        tilingPaddingHorizontal: 10,
                        initialEnergyOnIncremental: 0.3,
                        step: "all"
                    });

                    layout.run();
                },

                style: [
                    {
                        selector: 'node',
                        style: {
                            'background-color': '#2B65EC'
                        }
                    },

                    {
                        selector: 'edge',
                        style: {
                            'width': 3,
                            'line-color': '#2B65EC'
                        }
                    },

                    {
                        selector: 'node:selected',
                        style: {
                            'background-color': '#F08080',
                            'border-color': 'red'
                        }
                    },

                    {
                        selector: 'edge:selected',
                        style: {
                            'line-color': '#F08080'
                        }
                    }
                ],
            });

            // document.getElementById("randomizeButton").addEventListener("click", function () {
            //     var layout = cy.layout({
            //         name: 'random',
            //         animate: true,
            //         animationDuration: 1000
            //     });
            //
            //     layout.run();
            // });

            // document.getElementById("fcoseButton").addEventListener("click", function () {
            //     let qualityItem = "default";
            //     const layout = cy.layout({
            //         name: 'fcose',
            //         quality: "default",
            //         randomize: false,
            //         animate: true,
            //         animationEasing: 'ease-out',
            //         uniformNodeDimensions: false,
            //         packComponents: false,
            //         tile: true,
            //         nodeRepulsion: 4500,
            //         idealEdgeLength: 50,
            //         edgeElasticity: 0.45,
            //         nestingFactor: 0.1,
            //         gravity: 0.25,
            //         gravityRange: 3.8,
            //         gravityCompound: 1.0,
            //         gravityRangeCompound: 1.5,
            //         numIter: 2500,
            //         tilingPaddingVertical: 10,
            //         tilingPaddingHorizontal: 10,
            //         initialEnergyOnIncremental: 0.3,
            //         step: "all"
            //     });
            //
            //     layout.run();
            // });

            //Adding nodes to the layout
            cy.add(timelineNodes);

            //Adding a stylesheet to the network graph
            cy.style(network_stylesheet);
        }
    )

    // return (
        //     <div id="mySidepanel" class="sidepanel">
        //         <button id="randomizeButton" class="btn btn-primary btn-sm mb-2 ml-2">Randomize</button>
        // <button id="fcoseButton" class="btn btn-primary btn-sm mb-2">fCoSE</button>
        //     </div>
    // )

    return null;
}


export default NetworkGraphCard;