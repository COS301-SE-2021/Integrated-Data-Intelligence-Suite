import React, {Component, useRef, useState} from 'react';
import {Card} from "antd";
import {
    Map,
    TileLayer,
    Marker,
    Popup,
    FeatureGroup,
    LayerGroup,
    Rectangle,
    Circle,
    Tooltip,
    ScaleControl
} from 'react-leaflet'
import 'leaflet/dist/leaflet.css';
import ScriptTag from 'react-script-tag';
import L, {map} from "leaflet";
import {EditControl} from "react-leaflet-draw";
import "leaflet/dist/leaflet.css";
import "leaflet-draw/dist/leaflet.draw.css";
import cities from "../resources/cities.json";

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
  iconUrl: require("../resources/images/marker.png"),
  iconSize: [24, 24],
  iconAnchor: [17, 46], //[left/right, top/bottom]
  popupAnchor: [0, -46], //[left/right, top/bottom]
});

function MapCard() {
    const [mapLayers, setMapLayers] = useState([]);


    /*
       * runs when a polygon/marker/polyline is being Created
    */
    const _onCreate = (e) => {
        console.log(e);

        const {layerType, layer} = e;
        if (layerType === "polygon") {
            const {_leaflet_id} = layer;

            setMapLayers((layers) => [
                ...layers,
                {id: _leaflet_id, latlngs: layer.getLatLngs()[0]},
            ]);
        }

        //output
        console.log('on create');
        console.log(JSON.stringify(mapLayers));
    };

    /*
    * runs when a polygon/marker/polyline is being edited
    */
    const _onEdited = (e) => {
        console.log(e);
        const {
            layers: {_layers},
        } = e;

        Object.values(_layers).map(({_leaflet_id, editing}) => {
            setMapLayers((layers) =>
                layers.map((l) =>
                    l.id === _leaflet_id
                        ? {...l, latlngs: {...editing.latlngs[0]}}
                        : l
                )
            );

            //output
            console.log('on Edit');
            console.log(JSON.stringify(mapLayers));
        });
    };

    /*
    * Runs when a polygon/marker/polyline is being deleted
    */
    const _onDeleted = (e) => {
        console.log(e);
        const {
            layers: {_layers},
        } = e;

        Object.values(_layers).map(({_leaflet_id}) => {
            setMapLayers((layers) => layers.filter((l) => l.id !== _leaflet_id));
        });

        //output
        console.log('on Delete');
        console.log(JSON.stringify(mapLayers));
    };


    const animateRef = useRef(true)
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
                >
                    <TileLayer
                        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />

                    <ScaleControl position={"bottomleft"}/>
                    <FeatureGroup>
                        <EditControl
                            position="topleft"
                            onCreated={_onCreate}
                            onEdited={_onEdited}
                            onDeleted={_onDeleted}
                            draw={{}}
                        />
                    </FeatureGroup>

                    {cities.map((city, idx) => (
                        <Marker
                            position={[city.lat, city.lng]}
                            icon={markerIcon}
                            key={idx}
                        >
                            <Popup>
                                <b>
                                    {city.city}, {city.country}
                                </b>
                            </Popup>
                        </Marker>
                    ))}
                </Map>

                <pre className="text-left">{JSON.stringify(mapLayers)}</pre>
            </Card>

        </>
    );
}

export default MapCard;
