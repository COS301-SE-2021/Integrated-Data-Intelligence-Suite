import React, { useEffect, useRef, useState } from 'react';
import cytoscape from 'cytoscape/dist/cytoscape.cjs';
import fcose from 'cytoscape-fcose';
import cosebase from 'cose-base';
import timelineNodes from '../../resources/graphStructures/networkGraphNodes.json';
import './NetworkGraph.css';
import network_stylesheet from '../../resources/graphStructures/networkGraphNodesStyleSheet.json';
import layoutUtilities from 'cytoscape-layout-utilities';
import ScriptTag from 'react-script-tag';

const Demo = (props) => (
  <ScriptTag type="text/javascript" src="https://unpkg.com/cytoscape-layout-utilities/cytoscape-layout-utilities.js" />
);
cytoscape.use(fcose);

function NetworkGraphCard(props) {
  let cy;

  function run_layout_on_nodes() {
    cy.nodes().forEach((node) => {
      const size = Math.random() * 120 + 30;
      node.css('width', size);
      node.css('height', size);
    });
    cy.layout({ name: 'fcose' }).run();

    const layout = cy.layout({
      name: 'fcose',
      incremental: false,
      quality: 'default',
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
      step: 'all',
    });

    layout.run();
  }

  useEffect(() => {
    cy = cytoscape({
      container: document.getElementById('network_graph_card_content'), // container to render in

      ready() {
        // let layoutUtilities = this.layout{
        //     desiredAspectRatio: this.width() / this.height()
        // });

        this.nodes().forEach((node) => {
          const size = Math.random() * 120 + 30;
          node.css('width', size);
          node.css('height', size);
        });
        this.layout({ name: 'fcose' }).run();

        const layout = this.layout({
          name: 'fcose',
          incremental: false,
          quality: 'default',
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
          step: 'all',
        });

        layout.run();
      },

    });

    // //Adding nodes to the layout
    if (typeof props.text[1] === 'undefined') {
      // some error message
      console.log('array is undefined');
    } else if (props.text[1].length === 0) {
      // Some error
      console.log('array is empty');
    } else if (props.text[1].length > 0) {
      console.log('array is not empty');
      console.log(props.text[1]);
      cy.add(props.text[1]);

      run_layout_on_nodes();
      setTimeout(run_layout_on_nodes, 3100);
      run_layout_on_nodes();
    }

    // //Adding a stylesheet to the network graph
    cy.style(network_stylesheet);
  });

  // return (
  //     <div id="mySidepanel" class="sidepanel">
  //         <button id="randomizeButton" class="btn btn-primary btn-sm mb-2 ml-2">Randomize</button>
  // <button id="fcoseButton" class="btn btn-primary btn-sm mb-2">fCoSE</button>
  //     </div>
  // )

  return null;
}

export default NetworkGraphCard;
