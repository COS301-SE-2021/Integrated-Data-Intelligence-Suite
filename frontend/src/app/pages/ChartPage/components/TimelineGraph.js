import React from 'react';
import "../styles/TimelineGraph.css"
import {Chrono} from "react-chrono";
import timeline_cards from "../resources/data.json"

function TimelineGraph() {
    return (
        <Chrono
            items={timeline_cards}
            mode={"VERTICAL_ALTERNATING"}
            scrollable={true}
        />
    )
}

export default TimelineGraph;