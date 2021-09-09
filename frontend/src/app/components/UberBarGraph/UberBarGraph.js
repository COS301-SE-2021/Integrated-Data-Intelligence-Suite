import React from 'react';
import {
    XAxis,
    YAxis,
    FlexibleWidthXYPlot,
    HorizontalGridLines,
    LineSeries,
    VerticalRectSeries,
    DiscreteColorLegend,
    Crosshair, LineMarkSeries, VerticalRectSeriesCanvas, VerticalBarSeries, XYPlot
} from 'react-vis';
import './UberBarGraph.css';
/**
 * Get the array of x and y pairs.
 * The function tries to avoid too large changes of the chart.
 * @param {number} total Total number of values.
 * @returns {Array} Array of data.
 * @private
 */
function getRandomSeriesData(total) {
    const result = [];
    let lastY = Math.round(Math.random() * 40 - 20);
    let y;
    const firstY = lastY;
    for (let i = 0; i < Math.max(total, 3); i++) {
        y = Math.round(Math.random() * firstY - firstY / 2 + lastY);
        result.push({
            left: i,
            top: y
        });
        lastY = y;
    }
    return result;
}

const mock_data = [
    {
        title: 'Monday',
        x: 0,
        y: 8,
        isCurrentDay: 'no',
    },
    {
        title: 'Tuesday',
        x: 1,
        y: 5,
        isCurrentDay: 'no',
    },
    {
        title: 'Wednesday',
        x: 2,
        y: 4,
        isCurrentDay: 'no',
    },
    {
        title: 'Thursday',
        x: 3,
        y: 9,
        isCurrentDay: 'yes',
    },
    {
        title: 'Friday',
        x: 4,
        y: 13,
        isCurrentDay: 'no',
    }
];
const data = [...Array(5)
    .keys()].map(x => ({
    x,
    y: Math.random() * 10
}));
console.log(data);

//0 = color when hovered, 1 = color when not hovered
const colour_pallete = ['#FF991F', '#DAF9FB'];
let someVar = '';

export default class UberBarGraph extends React.Component {
    constructor(props) {
        super(props);
        const totalValues = 5;
        this.state = {
            index: 3,
            crosshairValues: [],
            series: [
                {
                    title: 'Parameter',
                    disabled: false,
                    data: mock_data
                }
            ]
        };
    }

    /**
     * A callback to format the crosshair items.
     * @param {Object} values Array of values.
     * @returns {Array<Object>} Array of objects with titles and values.
     * @private
     */
    _formatCrosshairItems = values => {
        const { series } = this.state;
        return values.map((v, i) => {
            return {
                title: series[i].title,
                value: v.y
            };
        });
    };

    _formatCrosshairTitle = values => {
        return {
            title: 'X',
            value: values[0].x
        };
    };

    _mouseLeaveHandler = () => {
        this.setState({
            crosshairValues: [],
            index: null
        });
        someVar = value.y;
    };
    /**
     * Event handler for onNearestX.
     * @param {Object} value Selected value.
     * @param {number} index Index of the series.
     * @private
     */
    _nearestXHandler = (value, { index }) => {
        this.setState({
            index: index
        });
        someVar = value.y;
    };

    render() {
        const {
            series,
            crosshairValues,
            index
        } = this.state;
        const dataWithColor = data.map((d, i) => ({
            ...d,
            color: Number(i !== index)
        }));

        return (
            <div className="uber-bar-graph-container">
                <p className={"bar-graph-statistic"}>{someVar}</p>
                <div className="bar-graph-plot">
                    <XYPlot
                        width={300}
                        height={150}
                        onMouseLeave={() => this.setState({ index: 3 })}
                        colorRange={colour_pallete}
                    >
                        <VerticalBarSeries
                            animation
                            data={dataWithColor}
                            onNearestX={this._nearestXHandler}
                            style={{ cornerRadius: '20' }}
                        />
                    </XYPlot>
                </div>
            </div>
        );
    }
}
