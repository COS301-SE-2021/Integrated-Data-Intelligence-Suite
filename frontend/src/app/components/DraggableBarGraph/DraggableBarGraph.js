import React from 'react';
import {
    XYPlot, XAxis, YAxis, VerticalRectSeries, Highlight, Crosshair, Hint,
} from 'react-vis';
import { data } from 'browserslist';
import { OLD_DATA, DATA } from '../../Mocks/BarGraphMock';

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
const index = 8;

export default class DraggableBarGraph extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selectionStart: null,
            selectionEnd: null,
            average: '',
            dataToBeDisplayed: [],
        };
    }
    componentDidMount() {
        if (this.props.text === '') {
            this.setState({ dataToBeDisplayed: OLD_DATA });
        } else if (this.props.text[index] && this.props.text[index] > 0) {
            this.setState({ dataToBeDisplayed: DATA });
        } else {
            this.setState({ dataToBeDisplayed: OLD_DATA });
        }
    }
    getAverageY() {
        if (this.state.selectionStart && this.state.selectionEnd) {
            const lowerBound = Math.floor(this.state.selectionStart);
            const upperBound = Math.ceil(this.state.selectionEnd) + 1;

            let avg = 0;

            // const highlighted_classes = []
            const lst = Object.values(this.state.dataToBeDisplayed)
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

    updateGraph() {
        if (this.props.text[index] && this.props.text[index].length > 0) {
            console.log(this.props.text[7]);
            console.log(this.props.text[8]);

            const lst = Object.values(this.props.text[index]).map((item, i) => {
                return {
                    x0: i,
                    x: i + 1,
                    y: item.y,
                    classname: `classname${i}`,
                    tweet: item.x,
                };
            });
            console.log(lst);
        } else {
            console.log('not a good check');
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
                {/* { this.props.text !== '' && this.updateGraph() } */}
                <div className="bar-graph-plot">
                    { this.props.text !== '' && (
                        <XYPlot width={300} height={200}>
                            <XAxis />
                            <YAxis />
                            <VerticalRectSeries
                              data={Object.values(this.props.text[index]).map((item, i) => ({ x0: i, x: i + 1, y: item.y, tweet: item.x }))}
                              stroke="white"
                              colorType="literal"
                              getColor={(d) => {
                                    if (selectionStart === null || selectionEnd === null) {
                                        return '#1E96BE';
                                    }
                                    const inX = d.x >= selectionStart && d.x <= selectionEnd;
                                    const inX0 = d.x - 1 >= selectionStart && d.x - 1 <= selectionEnd;
                                    const inStart = selectionStart >= d.x - 1 && selectionStart <= d.x;
                                    const inEnd = selectionEnd >= d.x - 1 && selectionEnd <= d.x;

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
                        </XYPlot>
                    )}
                    { this.props.text === '' && (
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
                                    const inX0 = d.x - 1 >= selectionStart && d.x - 1 <= selectionEnd;
                                    const inStart = selectionStart >= d.x - 1 && selectionStart <= d.x;
                                    const inEnd = selectionEnd >= d.x - 1 && selectionEnd <= d.x;

                                    return inStart || inEnd || inX || inX0 ? '#12939A' : '#1E96BE';
                                }}
                            />

                            <Highlight
                                color="#829AE3"
                                drag
                                enableY={false}
                                // onDrag={updateDragState}
                                // onDragEnd={updateDragState}

                            />
                        </XYPlot>
                    )}

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
