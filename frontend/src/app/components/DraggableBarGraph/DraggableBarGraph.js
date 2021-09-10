import React from 'react';
import {
    XYPlot, XAxis, YAxis, VerticalRectSeries, Highlight, Crosshair, Hint,
} from 'react-vis';
import { data } from 'browserslist';
import { DATA } from '../../Mocks/BarGraphMock';

function highlightMapPoints(highlighted_points) {
    const old_highlighted_points = document.getElementsByClassName('chosen_circle');
    for (let i = 0; i < old_highlighted_points.length; i++) {
        old_highlighted_points[i].classList.remove('chosen_circle');
    }

    for (let i = 0; i < highlighted_points.length; i++) {
        const points_to_change = document.getElementsByClassName(highlighted_points[i].classname);
        for (let j = 0; j < points_to_change.length; j++) {
            console.log(points_to_change[j]);
            points_to_change[j].classList.add('chosen_circle');
        }
    }
}

export default class DraggableBarGraph extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selectionStart: null,
            selectionEnd: null,
            average: '',
            dataPoints: [],
        };

        // TODO change data to [] or correct value from text array
        if (typeof props.text === 'undefined') {
            this.setState({ dataPoints: DATA });
        } else if (typeof props.text[0] === 'undefined') {
            // some error message
            this.setState({ dataPoints: DATA });
        } else if (props.text[0].length === 0) {
            this.setState({ dataPoints: DATA });
        } else if (props.text[0].length > 0) {
            this.setState({ dataPoints: DATA });
        }
    }

    getAverageY() {
        if (this.state.selectionStart && this.state.selectionEnd) {
            const lowerBound = Math.floor(this.state.selectionStart);
            const upperBound = Math.ceil(this.state.selectionEnd) + 1;

            let avg = 0;

            // const highlighted_classes = []
            const lst = Object.values(DATA)
                .filter((item) => {
                    if (item.x > lowerBound && item.x < upperBound) {
                        avg += item.y;
                        // highlighted_classes.push(item.classname);
                        return true;
                    }
                    return false;
                });

            avg /= lst.length;
            this.setState({ average: avg });
            console.log(lst);
            console.log(avg);
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
                <div className="bar-graph-plot">
                    <XYPlot width={300} height={200}>
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
                        {this.state.hoveredNode && selectionEnd && (
                            <Hint
                              align={{
                                    horizontal: 'left',
                                    vertical: 'top',
                                }}
                              value={{
                                    x: this.state.hoveredNode.x,
                                    y: this.state.hoveredNode.y,
                                    Average: this.state.hoveredNode.value,
                                }}
                            />
                        )}
                    </XYPlot>

                    {/* <div> */}
                    {/*    <b>selectionStart:</b> */}
                    {/*    {`${Math.floor(selectionStart * 100) / 100},`} */}
                    {/*    <b>selectionEnd:</b> */}
                    {/*    {`${Math.floor(selectionEnd * 100) / 100},`} */}
                    {/*    <b>Average of Y Values of Highlighted Bar Graphs</b> */}
                    {/* </div> */}
                </div>
            </div>
        );
    }
}
