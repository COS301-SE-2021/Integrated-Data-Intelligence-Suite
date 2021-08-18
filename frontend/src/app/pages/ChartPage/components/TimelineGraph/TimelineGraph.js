import React, {useEffect, Component} from 'react';
import "./TimelineGraph.css"
import {Chrono} from "react-chrono";
import timeline_cards from "../../resources/graphStructures/timelineData.json"

class TimelineGraph extends React.Component {
    constructor(props) {
        super(props);
        this.state = {data_from_backend: ''};
    }

    // useEffect(() => {
    //
    // })

    componentDidUpdate(prevProps) {
        // Typical usage (don't forget to compare props):
        // if (this.props.userID !== prevProps.userID) {
        //     this.fetchData(this.props.userID);
        // }
        console.log("componentDidUpdate is running");
    }

    render() {
        let data_from_backend = this.props.text[2];

        console.log("=======Timeline Graph===============");
        console.log("The data received from the backend:");
        console.log(this.props.text);
        console.log();
        if (typeof this.props.text[2] === 'undefined') {
            console.log()
            console.log("Data Received from Backend was undefined");

            //some error message
            data_from_backend = [];
        } else {
            if (this.props.text[2].length === 0) {
                console.log("Data Received from Backend was of length 0");

                //Some error message
                data_from_backend = [];

            } else if (this.props.text[2].length > 0) {
                console.log("Data Received from Backend was greater than length 0");
                data_from_backend = this.props.text[2];
                console.log(data_from_backend);
                // this.setState({data_from_backend: data_from_backend})


            }
        }

        return (
            <Chrono
                items={data_from_backend}
                mode={"VERTICAL_ALTERNATING"}
                scrollable={true}
            />
        )
    }
}

export default TimelineGraph;