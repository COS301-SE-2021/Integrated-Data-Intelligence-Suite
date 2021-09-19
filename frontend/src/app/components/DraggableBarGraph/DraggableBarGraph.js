import React from 'react';
import {
    XYPlot, XAxis, YAxis, VerticalRectSeries, Highlight, DiscreteColorLegend,
} from 'react-vis';

import { DATA } from '../../Mocks/BarGraphMock';

const index = 8;
const colors = {
    primary: '#5873f9',
    secondary: '#12939A',
};

const ITEMS = [
    { title: 'Options', color: '#ffffff' },
    { title: 'Buttons', color: '#ffffff' },
    { title: 'Select boxes', color: '#ffffff' },
    { title: 'Date inputs', color: '#ffffff' },
    { title: 'Password inputs', color: '#ffffff' },
    { title: 'Forms', color: '#ffffff' },
    { title: 'Other', color: '#ffffff' },
];

function highlightMapPoints(highlightedPoints) {
    const oldHighlightedPoints = document.getElementsByClassName('chosen_circle');
    for (let i = 0; i < oldHighlightedPoints.length; i += 1) {
        oldHighlightedPoints[i].classList.remove('chosen_circle');
    }

    for (let i = 0; i < highlightedPoints.length; i += 1) {
        const pointsToChange = document.getElementsByClassName(highlightedPoints[i].classname);
        for (let j = 0; j < pointsToChange.length; j += 1) {
            pointsToChange[j].classList.add('chosen_circle');
        }
    }
}

export default class DraggableBarGraph extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selectionStart: null,
            selectionEnd: null,
            dataToBeDisplayed: null,
            legendItems: null,
        };
    }
    static getDerivedStateFromProps(props) {
        if (props.text !== null && props.text !== '') {
            if (props.text[index] && props.text[index].length > 0) {
                const dataPoints = Object.values(props.text[index]).map((item, i) => ({
                    x0: i, x: i + 1, y: item.y, classname: item.x,
                }));
                const legendPoints = Object.values(props.text[index]).map((item, i) => ({
                    title: `${i}-${item.x}`, color: '#ffffff',
                }));
                return { dataToBeDisplayed: dataPoints, legendItems: legendPoints };
            }
        }

        return { dataToBeDisplayed: DATA, legendItems: [] };
    }

    getAverageY() {
        if (this.state.selectionStart && this.state.selectionEnd) {
            const lowerBound = Math.floor(this.state.selectionStart);
            const upperBound = Math.ceil(this.state.selectionEnd) + 1;

            const avg = 0;

            const lst = Object.values(this.state.dataToBeDisplayed)
                .filter((item) => item.x > lowerBound && item.x < upperBound);

            highlightMapPoints(lst);
        }
    }

    render() {
        const {
            selectionStart,
            selectionEnd,
        } = this.state;
        const updateDragState = (area) => {
            this.setState({
                selectionStart: area && area.left,
                selectionEnd: area && area.right,

            }, () => this.getAverageY());
        };

        return (
            <div className="uber-bar-graph-container">
                {/* {selectionStart && <p className="bar-graph-statistic">{this.state.average}</p>} */}
                {}
                <div className="bar-graph-plot">
                    { this.state.dataToBeDisplayed && (
                        <XYPlot width={300} height={200}>
                            <XAxis />
                            <YAxis />
                            <VerticalRectSeries
                              data={this.state.dataToBeDisplayed}
                              stroke="white"
                              colorType="literal"
                              getColor={(d) => {
                                    if (selectionStart === null || selectionEnd === null) {
                                        return colors.primary;
                                    }
                                    const inX = d.x >= selectionStart && d.x <= selectionEnd;
                                    const inX0 = d.x - 1 >= selectionStart && d.x - 1 <= selectionEnd;
                                    const inStart = selectionStart >= d.x - 1 && selectionStart <= d.x;
                                    const inEnd = selectionEnd >= d.x - 1 && selectionEnd <= d.x;

                                    return inStart || inEnd || inX || inX0 ? colors.secondary : colors.primary;
                                }}
                            />

                            <DiscreteColorLegend orientation="horizontal" items={this.state.legendItems} />

                            <Highlight
                              color="#829AE3"
                              drag
                              enableY={false}
                              onDrag={updateDragState}
                              onDragEnd={updateDragState}
                            />
                        </XYPlot>
                    )}
                </div>
            </div>
        );
    }
}
