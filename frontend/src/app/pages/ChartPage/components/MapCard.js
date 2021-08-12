import React, {Component, useRef} from 'react';
import {Card} from "antd";
import {
    MapContainer,
    TileLayer,
    Marker,
    Popup,
    FeatureGroup,
    LayerGroup,
    Rectangle,
    Circle,
    Tooltip, useMapEvent
} from 'react-leaflet'
import 'leaflet/dist/leaflet.css';



import ScriptTag from 'react-script-tag';

//Do not Change the order of these lines
//The Css MUST be loaded before the js
import '../../../components/leaflet/leaflet.css';
import {showBlueCircleData} from "../functions/showBlueCircleData";
import {showRedCircleData} from "../functions/showRedCircleData";
import {showGreenCircleData} from "../functions/showGreenCircleData";

const Demo = props => (
    <ScriptTag type="text/javascript" src="../../components/leaflet/leaflet.js"/>
)


const rectangle = [
    [-28.731340, 26.218370],
    [-20.731340, 26.218370],
]

const fillBlueOptions = {fillColor: 'blue'}
const fillRedOptions = {fillColor: 'red'}
const greenOptions = {color: 'green', fillColor: 'green'}
const purpleOptions = {color: 'purple'}


let pretoria_position = [-25.731340, 28.218370];

function SetViewOnClick({animateRef}) {
    const map = useMapEvent('click', (e) => {
        map.setView(e.latlng, map.getZoom(), {
            animate: animateRef.current || false,
        })
    })

    return null
}


function MapCard() {
    const animateRef = useRef(true)

    return (
        <>
            <Card
                id={'map_card'}
                title="Map Card Title"
                extra={<p></p>}
            >
                {/*<p>Card content</p>*/}
                <MapContainer
                    id={'map_container_div'}
                    center={pretoria_position}
                    zoom={9}
                    scrollWheelZoom={false}
                    style={{}}
                >
                    <TileLayer
                        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />


                    <LayerGroup>
                        <Circle
                            center={[-26.031340, 28.50000]}
                            pathOptions={fillBlueOptions}
                            radius={10000}
                            children={null}
                            prefixCls={null}
                            progressStatus={null}
                            eventHandlers={{
                                click: showBlueCircleData,

                            }}
                        >
                            <Tooltip>clickedText</Tooltip>

                        </Circle>


                        <Circle
                            center={pretoria_position}
                            pathOptions={fillRedOptions}
                            radius={10000}
                            stroke={false}
                            children={null}
                            eventHandlers={{
                                click: showRedCircleData,

                            }}
                        >
                            <Tooltip>clickedTex</Tooltip>

                        </Circle>


                        <LayerGroup>
                            <Circle

                                center={[-26.001340, 28.018370]}
                                pathOptions={greenOptions}
                                radius={10000}
                                children={null}
                                prefixCls={null}
                                progressStatus={null}
                                eventHandlers={{
                                    click: showGreenCircleData,

                                }}
                            >
                                <Tooltip>clickedText</Tooltip>

                            </Circle>
                        </LayerGroup>
                    </LayerGroup>

                    <SetViewOnClick animateRef={animateRef}/>
                </MapContainer>
            </Card>

        </>
    );
}

export default MapCard;
