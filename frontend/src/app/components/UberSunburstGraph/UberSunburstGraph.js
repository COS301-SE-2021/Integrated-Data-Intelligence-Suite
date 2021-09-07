import React from 'react';

import { Sunburst, LabelSeries } from 'react-vis';
import { EXTENDED_DISCRETE_COLOR_RANGE } from '../../resources/theme';
import D3FlareData from '../../datasets/d3-flare-example.json';

const LABEL_STYLE = {
    fontSize: '8px',
    textAnchor: 'middle'
};

/**
 * Recursively work backwards from highlighted node to find path of valud nodes
 * @param {Object} node - the current node being considered
 * @returns {Array} an array of strings describing the key route to the current node
 */
function getKeyPath(node) {
    if (!node.parent) {
        return ['root'];
    }

    return [(node.data && node.data.name) || node.name].concat(
        getKeyPath(node.parent)
    );
}

/**
 * Recursively modify data depending on whether or not each cell has been selected by the hover/highlight
 * @param {Object} data - the current node being considered
 * @param {Object|Boolean} keyPath - a map of keys that are in the highlight path
 * if this is false then all nodes are marked as selected
 * @returns {Object} Updated tree structure
 */
function updateData(data, keyPath) {
    if (data.children) {
        data.children.map(child => updateData(child, keyPath));
    }
    // add a fill to all the uncolored cells
    if (!data.hex) {
        data.style = {
            fill: EXTENDED_DISCRETE_COLOR_RANGE[5]
        };
    }
    data.style = {
        ...data.style,
        fillOpacity: keyPath && !keyPath[data.name] ? 0.2 : 1
    };

    return data;
}

const decoratedData = updateData(D3FlareData, false);

export default class UberSunburstGraph extends React.Component {
    state = {
        pathValue: false,
        data: decoratedData,
        finalValue: 'SUNBURST',
        clicked: false
    };

    render() {
        const {
            clicked,
            data,
            finalValue,
            pathValue
        } = this.state;
        return (
            <div className="basic-sunburst-example-wrapper">
                <div>
                    {clicked ? 'click to unlock selection' : 'click to lock selection'}
                </div>
                <Sunburst
                    animation
                    className="basic-sunburst-example"
                    hideRootNode
                    onValueMouseOver={node => {
                        if (clicked) {
                            return;
                        }

                        const path = getKeyPath(node).reverse();

                        const pathAsMap = path.reduce((res, row) => {
                            res[row] = true;
                            return res;
                        }, {});

                        this.setState({
                            finalValue: path[path.length - 1],
                            pathValue: path.join(' > '),
                            data: updateData(decoratedData, pathAsMap)
                        });
                    }}
                    onValueMouseOut={() => clicked
                        ? () => {
                        }
                        : this.setState({
                            pathValue: false,
                            finalValue: false,
                            data: updateData(decoratedData, false)
                        })}
                    onValueClick={() => this.setState({ clicked: !clicked })}
                    style={{
                        stroke: '#ddd',
                        strokeOpacity: 0.3,
                        strokeWidth: '0.5'
                    }}
                    colorType="literal"
                    getSize={d => d.value}
                    getColor={d => d.hex}
                    data={data}
                    height={300}
                    width={350}
                >
                    {finalValue && (
                        <LabelSeries
                            data={[{
                                x: 0,
                                y: 0,
                                label: finalValue,
                                style: LABEL_STYLE
                            }]}
                        />
                    )}
                </Sunburst>
                <div className="basic-sunburst-example-path-name">{pathValue}</div>
            </div>
        );
    }
}
