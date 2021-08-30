import React, { useEffect, useRef, useState } from 'react';
import 'leaflet/dist/leaflet.css';
import ScriptTag from 'react-script-tag';
import L, { map } from 'leaflet';
import {
    Marker,
    Map,
    FeatureGroup,
    Circle,
    TileLayer,
    Popup,
    CircleMarker,
    LayersControl,
} from 'react-leaflet';
import 'leaflet-draw/dist/leaflet.draw.css';
import './leaflet/leaflet.css';
import 'leaflet-snap/leaflet.snap.js';
import { EditControl } from 'react-leaflet-draw';
import { Card } from 'antd';
import { geosearch } from 'esri-leaflet-geocoder';
import 'esri-leaflet-geocoder/dist/esri-leaflet-geocoder.css';

import datapoints from '../../resources/graphStructures/mapDatapoints.json';
import { showCircleData } from '../../functions/showCircleData';
import { getDatapointsWithinBoundsOfLayer } from '../../functions/getDatapointsWithinBoundsOfLayer';

// Do not Change the order of these lines
// The Css MUST be loaded before the js
import 'leaflet-geometryutil/src/leaflet.geometryutil.js';
import 'leaflet-draw/dist/leaflet.draw.js';

const Demo = (props) => (
    <ScriptTag type="text/javascript" src="../../components/leaflet/leaflet.js" />
);
const pretoria_position = [-25.731340, 28.218370];

/*
* updating the default map marker icons in the leaflet library
* */
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl:
        'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png',
    iconUrl:
        'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png',
    shadowUrl:
        'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-shadow.png',
});

const markerIcon = new L.Icon({
    iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png',
    iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png',
    iconSize: [25, 41],
    iconAnchor: [17, 46], // [left/right, top/bottom]
    popupAnchor: [0, -46], // [left/right, top/bottom]
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-shadow.png',
});

function MapCard(props) {
    let data_from_backend;
    if (typeof props.text === 'undefined') {
        data_from_backend = [];
    } else if (typeof props.text[0] === 'undefined') {
        // some error message
        data_from_backend = [];
    } else if (props.text[0].length === 0) {
        data_from_backend = [];
    } else if (props.text[0].length > 0) {
        data_from_backend = props.text[0];
    }
    const [mapLayers, setMapLayers] = useState([]);

    const mapRef = useRef();

    useEffect(() => {
        const { current = {} } = mapRef;
        const { leafletElement: map } = current;

        if (!map) return;

        const control = geosearch();

        control.addTo(map);
    }, []);

    function _created(e) {
        console.log('================_created() start==========');
        console.log(e);
        let layers = L;
        console.log(layers);
        console.log('================_created() End==========');
    }

    const animateRef = useRef(true);
    let poly;
    return (
        <>
            <Card
              id="map_card"
              title="Map"
                // extra={<p/>}
            >
                {/* <p>Card content</p> */}
                <Map
<<<<<<< HEAD
                    id="map_container_div"
                    center={pretoria_position}
                    zoom={9}
                    scrollWheelZoom
                    ref={mapRef}
                    animate
=======
                  id="map_container_div"
                  center={pretoria_position}
                  zoom={9}
                  scrollWheelZoom
                  ref={mapRef}
>>>>>>> origin/feature-ui
                >
                    <LayersControl
                      position="topright"
                      collapsed={false}
                    >
                        <LayersControl.BaseLayer name="osm b&w">
                            <TileLayer
                              attribution='<a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                              url="https://tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png"
                            />
                        </LayersControl.BaseLayer>
                        <LayersControl.BaseLayer name="osm colour" checked>
                            <TileLayer
                              attribution='<a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                            />
                        </LayersControl.BaseLayer>
                        <LayersControl.BaseLayer name="google">
                            <TileLayer
                              attribution="google"
                              url="http://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x={x}&y={y}&z={z}  "
                            />
                        </LayersControl.BaseLayer>

                        <LayersControl.Overlay name="drawn items" checked>
                            <FeatureGroup>
                                <EditControl
                                  position="topleft"
                                  onCreated={_created}
                                  draw={{}}
                                />
                            </FeatureGroup>
                        </LayersControl.Overlay>
                    </LayersControl>

                    {/* Display the City markers onto the map */}
                    {data_from_backend.map((city, idx) => (
                        <CircleMarker
                          center={[city.lat, city.lng]}
                          icon={markerIcon}
                          key={idx}
                          className={city.classname}
                          onClick={() => {
                                showCircleData(city.classname, data_from_backend);
                            }}
                        />
                    ))}
                </Map>
            </Card>
        </>
    );
}

export default MapCard;
