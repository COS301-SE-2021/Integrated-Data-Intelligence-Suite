import React, {useEffect, useRef, useState} from 'react';
import 'leaflet/dist/leaflet.css';
import ScriptTag from 'react-script-tag';
import L, {map} from "leaflet";
import {Marker, Map, FeatureGroup, Circle, TileLayer, Popup, CircleMarker} from 'react-leaflet';
import "leaflet/dist/leaflet.css";
import "leaflet-draw/dist/leaflet.draw.css";
import './leaflet/leaflet.css';
import 'leaflet-snap/leaflet.snap.js';
import datapoints from "../../resources/graphStructures/mapDatapoints.json"
import {showCircleData} from "../../functions/showCircleData";

//Do not Change the order of these lines
//The Css MUST be loaded before the js
import "leaflet-geometryutil/src/leaflet.geometryutil.js";
import "leaflet-draw/dist/leaflet.draw.js";
import {EditControl} from "react-leaflet-draw";
import {Card} from "antd";

const Demo = props => (
    <ScriptTag type="text/javascript" src="../../components/leaflet/leaflet.js"/>
    // <ScriptTag type="text/javascript" src="../../"/>
)
let pretoria_position = [-25.731340, 28.218370];

/*
* updating the default map marker icons in the leaflet library
* */
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png",
    iconUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png",
    shadowUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-shadow.png",
});

const markerIcon = new L.Icon({
    iconUrl: "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png",
    iconRetinaUrl: "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png",
    iconSize: [25, 41],
    iconAnchor: [17, 46], //[left/right, top/bottom]
    popupAnchor: [0, -46], //[left/right, top/bottom]
    shadowUrl: "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-shadow.png",
});


function MapCard(props) {
    // console.log("=======Start of Map Card===============");
    // console.log("The data received from the backend:");
    // console.log(props.text);
    // console.log();


    let data_from_backend;
    if (typeof props.text === 'undefined') {
        data_from_backend = [];
    } else {
        if (typeof props.text[0] === 'undefined') {
            // console.log()
            // console.log("Data Received from Backend was undefined");

            //some error message
            data_from_backend = [];
        } else {
            if (props.text[0].length === 0) {
                // console.log("Data Received from Backend was of length 0");

                //Some error message
                data_from_backend = [];

            } else if (props.text[0].length > 0) {
                data_from_backend = props.text[0];
            }
        }
    }
    const [mapLayers, setMapLayers] = useState([]);

    const animateRef = useRef(true)
    let poly;
    return (
        <>
            <Card
                id={'map_card'}
                title="Map Card Title"
                extra={<p></p>}
            >
                {/*<p>Card content</p>*/}
                <Map
                    id={'map_container_div'}
                    center={pretoria_position}
                    zoom={9}
                    scrollWheelZoom={true}
                    // ref={map => this.map = Map}
                >
                    <TileLayer
                        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />

                    {/*<FeatureGroup>*/}
                    {/*    <EditControl*/}
                    {/*        position="topleft"*/}
                    {/*        onCreated={_onCreate}*/}
                    {/*        onEdited={_onEdited}*/}
                    {/*        onDeleted={_onDeleted}*/}
                    {/*        draw={{}}*/}
                    {/*    />*/}
                    {/*</FeatureGroup>*/}

                    {/*Display the City markers onto the map*/}
                    {data_from_backend.map((city, idx) => (
                        <CircleMarker
                            center={[city.lat, city.lng]}
                            icon={markerIcon}
                            key={idx}
                            className={city.classname}
                            onClick={() => {
                                showCircleData(city.classname, data_from_backend)
                            }}
                        >
                            <Popup>
                                <b>
                                    {city.classname}
                                </b>
                            </Popup>
                        </CircleMarker>
                    ))}


                </Map>

                {/*<pre className="text-left">{JSON.stringify(mapLayers)}</pre>*/}
            </Card>

        </>
    );


}

export default MapCard;
