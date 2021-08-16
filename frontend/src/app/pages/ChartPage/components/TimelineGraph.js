import React, {useEffect, useRef, useState} from 'react';
import "../styles/TimelineGraph.css"
import ScriptTag from 'react-script-tag';
import {Chrono} from "react-chrono";
import timeline_cards from "../resources/data.json"


function TimelineGraph() {
    // const items = [{
    //     title: "May 1940",
    //     cardTitle: "Dunkirk",
    //     cardSubtitle: "Men of the British Expeditionary Force (BEF) wade out to..",
    //     cardDetailedText: "Men of the British Expeditionary Force (BEF) wade out to..",
    //     media: {
    //         type: "IMAGE",
    //         source: {
    //             url: "http://someurl/image.jpg"
    //         }
    //     }
    // }];

    return (
        // <div style={{width: "500px", height: "400px"}}>
            <Chrono
                items={timeline_cards}
                mode={"VERTICAL_ALTERNATING"}
                scrollable={true}
            />
        // </div>
    )
}

export default TimelineGraph;