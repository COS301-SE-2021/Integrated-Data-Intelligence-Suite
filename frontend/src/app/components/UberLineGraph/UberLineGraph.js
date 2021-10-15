import React from 'react';

import {
    XAxis,
    YAxis,
    FlexibleWidthXYPlot,
    HorizontalGridLines,
    LineSeries,
    VerticalRectSeries,
    DiscreteColorLegend,
    Crosshair, LineMarkSeries, VerticalRectSeriesCanvas
} from 'react-vis';

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
const colour_pallete = ['#FF991F', '#DAF9FB'];

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

export default class UberLineGraph extends React.Component {
    constructor(props) {
        super(props);
        const totalValues = 5;
        this.state = {
            crosshairValues: [],
            series: [
                {
                    title: props.crossHairTitle,
                    disabled: false,
                    data: mock_data
                }
            ],
            index: null
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
                title: '',
                value: ''
            };
        });
    };

    /**
     * Format the title line of the crosshair.
     * @param {Array} values Array of values.
     * @returns {Object} The caption and the value of the title.
     * @private
     */
    _formatCrosshairTitle = values => {
        return {
            title: values[0].title,
            value: values[0].y
        };
    };

    /**
     * Event handler for onMouseLeave.
     * @private
     */
    _mouseLeaveHandler = () => {
        this.setState({
            crosshairValues: [],
            index: null
        });
    };

    _nearestXHandler = (value, { index }) => {
        const { series } = this.state;
        this.setState({
            crosshairValues: series.map(s => s.data[index]),
            index
        });
    };

    render() {
        const {
            series,
            crosshairValues,
            index
        } = this.state;

        const data_with_color = mock_data.map((d, i) => ({
            ...d,
            stroke: Number(i === index) ? 0 : 1
        }));

        // console.log(data_with_color);
        return (
            <div className="example-with-click-me">
                <div className="chart">
                    <FlexibleWidthXYPlot
                        animation
                        getX={d => d.x}
                        getY={d => d.y}
                        onMouseLeave={this._mouseLeaveHandler}
                        height={200}

                    >
                        <YAxis
                            className="cool-custom-name"
                        />
                        <XAxis
                            className="even-cooler-custom-name"
                            tickSizeInner={0}
                            tickSizeOuter={8}
                        />
                        <LineMarkSeries
                            data={data_with_color}
                            colorType="literal"
                            style={{
                                strokeWidth: '0.5px'
                            }}
                            // lineStyle={{ stroke: 'lightblue' }}
                            // markStyle={{ stroke: 'yellow' }}
                            curve="curveMonotoneX"
                            {...(series[0].disabled ? { opacity: 0.2 } : null)}
                            onNearestX={this._nearestXHandler}
                            colorRange={colour_pallete}
                        />
                    </FlexibleWidthXYPlot>
                </div>

                {/*<button className="click-me" onClick={this._updateButtonClicked}>*/}
                {/*    Click to update*/}
                {/*</button>*/}
            </div>
        );
    }
}
