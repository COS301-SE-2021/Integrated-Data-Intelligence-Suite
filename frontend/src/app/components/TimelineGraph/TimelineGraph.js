import React, { useEffect, Component } from 'react';
import './TimelineGraph.css';
import { Chrono } from 'react-chrono';
import timeline_cards from '../../resources/graphStructures/timelineData.json';

export default class TimelineGraph extends React.Component {
    constructor(props) {
        super(props);
        this.state = { data_from_backend: '' };
    }

    componentDidUpdate(prevProps) {
        console.log('componentDidUpdate is running');
    }

    render() {
        let data_from_backend;
        if (typeof this.props.text === 'undefined') {
            data_from_backend = [];
        } else if (typeof this.props.text[13] === 'undefined') {
            data_from_backend = [];
        } else if (this.props.text[13].length === 0) {
            console.log('Data Received from Backend was of length 0');
            data_from_backend = [];
        } else if (this.props.text[13].length > 0) {
            data_from_backend = this.props.text[13];
        }

        return (
            <Chrono
                items={data_from_backend}
                mode="VERTICAL"
                scrollable
            />
        );
    }
}
