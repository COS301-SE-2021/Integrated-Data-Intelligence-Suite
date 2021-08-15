import React, {useEffect, useRef, useState} from 'react';
import cytoscape from "cytoscape/dist/cytoscape.cjs";
import fcose from "cytoscape-fcose";
import cosebase from "cose-base";
import timelineNodes from "../resources/networkGraphNodes.json";
import "../styles/NetworkGraph.css";
import network_stylesheet from "../resources/networkGraphNodesStyleSheet.json";
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

                elements: [{
                    "data": {
                        "id": "glyph9",
                        "position": {"x": 1452.639173965406, "y": 609.3619416544145},
                        "group": "nodes"
                    }
                },
                    {
                        "data": {
                            "id": "glyph0",
                            "position": {"x": 1351.3490293961959, "y": 518.9529901384763},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph6",
                            "position": {"x": 1358.2854747390154, "y": 707.9866590968695},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph8",
                            "position": {"x": 1322.9939787691299, "y": 614.6878118623499},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph7",
                            "position": {"x": 1239.4852011317887, "y": 543.2369849876238},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph12",
                            "position": {"x": 841.6855140740067, "y": 765.0152660242113},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph13",
                            "position": {"x": 1019.5908382748769, "y": 841.6087025848726},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph1",
                            "position": {"x": 1231.2768042260652, "y": 673.2683218469676},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph2",
                            "position": {"x": 1039.8995038336504, "y": 730.180116446269},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph15",
                            "position": {"x": 569.5498472077387, "y": 506.89980858075364},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph3",
                            "position": {"x": 903.0347368937041, "y": 654.3308627056822},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph17",
                            "position": {"x": 1195.6310733031135, "y": 820.9504141631944},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph10",
                            "position": {"x": 1141.2404374322139, "y": 732.3190922346248},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph19",
                            "position": {"x": 893.1427762830865, "y": 856.2695126662625},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph11",
                            "position": {"x": 939.3335184518824, "y": 758.3699048922733},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph18",
                            "position": {"x": 770.4114528170364, "y": 659.2220219290564},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph16",
                            "position": {"x": 818.0111009023315, "y": 564.8072603606723},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph22",
                            "position": {"x": 651.1292498357636, "y": 314.1387423188818},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph4",
                            "position": {"x": 792.0076145303351, "y": 454.0225025614517},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph23",
                            "position": {"x": 704.0937009722281, "y": 398.0421081673902},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph24",
                            "position": {"x": 809.2974819306742, "y": 231.7141323534711},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph25",
                            "position": {"x": 890.826951363933, "y": 299.74915938409947},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph20",
                            "position": {"x": 786.2625869125006, "y": 331.67766378118495},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph26",
                            "position": {"x": 879.2981049664311, "y": 389.27232563593486},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph35",
                            "position": {"x": 627.088268638501, "y": 40.089848876876886},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph36",
                            "position": {"x": 329.6761506918384, "y": 187.20503497360494},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph37",
                            "position": {"x": 155.12947729633356, "y": 379.5263531900425},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph38",
                            "position": {"x": 70.13952165372024, "y": 581.2691021233562},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph21",
                            "position": {"x": 713.4639263718316, "y": 229.06355211274115},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph42",
                            "position": {"x": 523.848994074475, "y": 108.47701882803744},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph41",
                            "position": {"x": 718.966532806447, "y": 116.46683749236911},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph31",
                            "position": {"x": 621.3138039842713, "y": 145.7168752444793},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph27",
                            "position": {"x": 525.2099120385327, "y": 210.92542274858295},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph32",
                            "position": {"x": 426.3492127437995, "y": 257.85665030680025},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph28",
                            "position": {"x": 346.30926488002945, "y": 344.4562152937847},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph43",
                            "position": {"x": 363.54724181648487, "y": 486.5705174517715},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph33",
                            "position": {"x": 269.87972487503066, "y": 430.2423722580144},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph29",
                            "position": {"x": 227.86139816113416, "y": 531.824141876398},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph39",
                            "position": {"x": 104.77693104995387, "y": 691.8382969303054},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph40",
                            "position": {"x": 292.039416141131, "y": 643.4009391289965},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph34",
                            "position": {"x": 193.8304385062596, "y": 632.9540034207419},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph30",
                            "position": {"x": 205.4745704273754, "y": 733.5181650652648},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph14",
                            "position": {"x": 695.1248473196924, "y": 482.8828321494848},
                            "group": "nodes"
                        }
                    },
                    {
                        "data": {
                            "id": "glyph5",
                            "position": {"x": 721.6687687330186, "y": 570.3868893775194},
                            "group": "nodes"
                        }
                    },
                    {"data": {"id": "e22", "source": "glyph9", "target": "glyph8", "group": "edges"}},
                    {"data": {"id": "e23", "source": "glyph0", "target": "glyph8", "group": "edges"}},
                    {"data": {"id": "e24", "source": "glyph8", "target": "glyph1", "group": "edges"}},
                    {"data": {"id": "e25", "source": "glyph6", "target": "glyph8", "group": "edges"}},
                    {"data": {"id": "e26", "source": "glyph8", "target": "glyph7", "group": "edges"}},
                    {"data": {"id": "e27", "source": "glyph11", "target": "glyph12", "group": "edges"}},
                    {"data": {"id": "e28", "source": "glyph13", "target": "glyph11", "group": "edges"}},
                    {"data": {"id": "e29", "source": "glyph1", "target": "glyph10", "group": "edges"}},
                    {"data": {"id": "e30", "source": "glyph10", "target": "glyph2", "group": "edges"}},
                    {"data": {"id": "e31", "source": "glyph2", "target": "glyph11", "group": "edges"}},
                    //{"data":{"id":"e32","source":"glyph11","target":"glyph3","group":"edges"}},
                    {"data": {"id": "e33", "source": "glyph14", "target": "glyph4", "group": "edges"}},
                    {"data": {"id": "e34", "source": "glyph15", "target": "glyph14", "group": "edges"}},
                    {"data": {"id": "e35", "source": "glyph3", "target": "glyph16", "group": "edges"}},
                    {"data": {"id": "e36", "source": "glyph16", "target": "glyph5", "group": "edges"}},
                    {"data": {"id": "e37", "source": "glyph16", "target": "glyph4", "group": "edges"}},
                    {"data": {"id": "e38", "source": "glyph17", "target": "glyph10", "group": "edges"}},
                    {"data": {"id": "e39", "source": "glyph19", "target": "glyph11", "group": "edges"}},
                    {"data": {"id": "e40", "source": "glyph18", "target": "glyph16", "group": "edges"}},
                    //{"data":{"id":"e41","source":"glyph22","target":"glyph20","group":"edges"}},
                    {"data": {"id": "e42", "source": "glyph4", "target": "glyph20", "group": "edges"}},
                    {"data": {"id": "e43", "source": "glyph20", "target": "glyph21", "group": "edges"}},
                    {"data": {"id": "e44", "source": "glyph23", "target": "glyph20", "group": "edges"}},
                    {"data": {"id": "e45", "source": "glyph24", "target": "glyph20", "group": "edges"}},
                    {"data": {"id": "e46", "source": "glyph20", "target": "glyph25", "group": "edges"}},
                    {"data": {"id": "e47", "source": "glyph20", "target": "glyph26", "group": "edges"}},
                    {"data": {"id": "e48", "source": "glyph35", "target": "glyph31", "group": "edges"}},
                    {"data": {"id": "e49", "source": "glyph36", "target": "glyph32", "group": "edges"}},
                    //{"data":{"id":"e50","source":"glyph37","target":"glyph33","group":"edges"}},
                    {"data": {"id": "e51", "source": "glyph38", "target": "glyph34", "group": "edges"}},
                    {"data": {"id": "e52", "source": "glyph21", "target": "glyph31", "group": "edges"}},
                    {"data": {"id": "e53", "source": "glyph42", "target": "glyph31", "group": "edges"}},
                    {"data": {"id": "e54", "source": "glyph31", "target": "glyph41", "group": "edges"}},
                    {"data": {"id": "e55", "source": "glyph31", "target": "glyph27", "group": "edges"}},
                    {"data": {"id": "e56", "source": "glyph27", "target": "glyph32", "group": "edges"}},
                    {"data": {"id": "e57", "source": "glyph32", "target": "glyph28", "group": "edges"}},
                    {"data": {"id": "e58", "source": "glyph28", "target": "glyph33", "group": "edges"}},
                    //{"data":{"id":"e59","source":"glyph33","target":"glyph43","group":"edges"}},
                    //{"data":{"id":"e60","source":"glyph33","target":"glyph29","group":"edges"}},
                    {"data": {"id": "e61", "source": "glyph29", "target": "glyph34", "group": "edges"}},
                    //{"data":{"id":"e62","source":"glyph39","target":"glyph34","group":"edges"}},
                    {"data": {"id": "e63", "source": "glyph34", "target": "glyph40", "group": "edges"}},
                    {"data": {"id": "e64", "source": "glyph34", "target": "glyph30", "group": "edges"}},
                    {"data": {"id": "e65", "source": "glyph14", "target": "glyph5", "group": "edges"}},
                    {"data": {"id": "e66", "source": "glyph33", "target": "glyph35", "group": "edges"}},
                    {"data": {"id": "e67", "source": "glyph13", "target": "glyph22", "group": "edges"}},
                    {"data": {"id": "e68", "source": "glyph17", "target": "glyph6", "group": "edges"}},
                    {"data": {"id": "e69", "source": "glyph25", "target": "glyph27", "group": "edges"}}],


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

            // //Adding nodes to the layout
            // cy.add(timelineNodes);
            //
            // //Adding a stylesheet to the network graph
            // cy.style(network_stylesheet);
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