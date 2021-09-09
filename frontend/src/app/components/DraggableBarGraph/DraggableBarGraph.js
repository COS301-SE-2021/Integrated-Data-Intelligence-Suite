import React from 'react';
import {
    XYPlot, XAxis, YAxis, VerticalRectSeries, Highlight, Crosshair, Hint,
} from 'react-vis';

const DATA = [
    {
        x0: 0,
        x: 1,
        y: 1,
    },
    {
        x0: 1,
        x: 2,
        y: 2,
    },
    {
        x0: 2,
        x: 3,
        y: 10,
    },
    {
        x0: 3,
        x: 4,
        y: 6,
    },
    {
        x0: 4,
        x: 5,
        y: 5,
    },
    {
        x0: 5,
        x: 6,
        y: 3,
    },
    {
        x0: 6,
        x: 7,
        y: 1,
    },
];

export default class DraggableBarGraph extends React.Component {
    constructor() {
        super();
        this.state = {
            selectionStart: null,
            selectionEnd: null,
            crosshairValues: [],
            hoveredNode: null,
        };
    }

    getAverageY() {
        if (this.state.selectionStart && this.state.selectionEnd) {
            const lowerBound = Math.floor(this.state.selectionStart);
            const upperBound = Math.ceil(this.state.selectionEnd) + 1;

            let sum = 0;
            let yMax = -1000
            const lst = Object.values(DATA).filter((item) => {
                if (yMax < item.y) {
                    yMax = item.y;
                }
                if (item.x > lowerBound && item.x < upperBound) {
                    sum += item.y;
                    return true;
                }
                return false;
            });

            sum /= lst.length;
            this.state.hoveredNode = {
                x: DATA[0].x0,
                y: yMax,
                value: sum,
            }
            console.log(lst);
            console.log(sum);
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

        const barGraphHasBeenClicked = (d, { event }) => {
            console.log('A bar graph has been clicked Below is the D value and event:');
            console.log(d);
            console.log(event);
            console.log();
        };

        return (
            <div>
                <XYPlot width={500} height={300}>
                    <XAxis />
                    <YAxis />
                    <VerticalRectSeries
                      data={DATA}
                      stroke="white"
                      colorType="literal"
                      getColor={(d) => {
                            if (selectionStart === null || selectionEnd === null) {
                                return '#1E96BE';
                            }
                            const inX = d.x >= selectionStart && d.x <= selectionEnd;
                            const inX0 = d.x0 >= selectionStart && d.x0 <= selectionEnd;
                            const inStart = selectionStart >= d.x0 && selectionStart <= d.x;
                            const inEnd = selectionEnd >= d.x0 && selectionEnd <= d.x;

                            return inStart || inEnd || inX || inX0 ? '#12939A' : '#1E96BE';
                        }}
                    />

                    <Highlight
                      color="#829AE3"
                      drag
                      enableY={false}
                      onDrag={updateDragState}
                      onDragEnd={updateDragState}
                    />
                    { this.state.hoveredNode && selectionEnd && (
                        <Hint
                          value={{
                                x: this.state.hoveredNode.x,
                                y: this.state.hoveredNode.y,
                                Average: this.state.hoveredNode.value,
                            }}
                        />
                    )}
                </XYPlot>

                <div>
                    <b>selectionStart:</b>
                    {`${Math.floor(selectionStart * 100) / 100},`}
                    <b>selectionEnd:</b>
                    {`${Math.floor(selectionEnd * 100) / 100},`}
                    <b>Average of Y Values of Highlighted Bar Graphs</b>
                </div>
            </div>
        );
    }
}
