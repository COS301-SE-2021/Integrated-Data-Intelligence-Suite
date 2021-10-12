import React from 'react';
import { Card } from 'antd';
import networkStyleSheet from '../../resources/graphStructures/networkGraphNodesStyleSheet.json';
import CytoscapeComponent from './utils/cytoscape';
import './NetworkGraph.css';


const NetworkGraphCard = ({ graphData }) => {
    const myLayout = { name: 'cose-bilkent' };
    return (
        <>
            {
                graphData &&
                (
                    <Card
                      id="network_card"
                      title=""
                      style={{ border: '0' }}
                    >
                        <CytoscapeComponent
                          elements={graphData}
                          stylesheet={networkStyleSheet}
                          layout={myLayout}
                          style={{
                                width: '100%',
                                height: '400px',
                            }}
                          cy={(cy) => {
                                cy.on('add', 'node', () => {
                                    cy.layout(myLayout)
                                        .run();
                                    cy.fit();
                                });
                            }}
                        />
                    </Card>
                )
            }
        </>
    );
};

export default NetworkGraphCard;
