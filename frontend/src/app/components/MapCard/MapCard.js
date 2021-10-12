import React, { useRef } from 'react';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import {
    Map,
    FeatureGroup,
    TileLayer,
    CircleMarker,
} from 'react-leaflet';
import 'leaflet-draw/dist/leaflet.draw.css';
import './leaflet/leaflet.css';
import 'leaflet-snap/leaflet.snap.js';
import { EditControl } from 'react-leaflet-draw';
import 'esri-leaflet-geocoder/dist/esri-leaflet-geocoder.css';

// Do not Change the order of these lines
// The Css MUST be loaded before the js
import 'leaflet-geometryutil/src/leaflet.geometryutil.js';
import 'leaflet-draw/dist/leaflet.draw.js';
import { useRecoilValue } from 'recoil';
import { mapDataState } from '../../assets/AtomStore/AtomStore';

const defaultPosition = [-25.731340, 28.218370];

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

function MapCard() {
    const mapData = useRecoilValue(mapDataState);
    const mapRef = useRef();

    function _created(e) {
        console.log('================_created() start==========');
        console.log(e);
        const layers = L;
        console.log(layers);
        console.log('================_created() End==========');
    }

    return (
        <>
            <Map
              id="map_container_div"
              center={defaultPosition}
              zoom={5}
              scrollWheelZoom
              ref={mapRef}
              animate
            >
                {/* <TileLayer */}
                {/*  attribution='<a href="http://osm.org/copyright">OpenStreetMap</a> contributors' */}
                {/*  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" */}
                {/* /> */}
                <TileLayer
                  attribution='&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a> &copy; <a href="http://cartodb.com/attributions">CartoDB</a>'
                  url="https://cartodb-basemaps-{s}.global.ssl.fastly.net/dark_all/{z}/{x}/{y}.png"
                />
                <FeatureGroup>
                    <EditControl
                      position="topleft"
                      onCreated={_created}
                      draw={{
                            circlemarker: false,
                        }}
                    />
                </FeatureGroup>
                {mapData.map((city, idx) => (
                    <CircleMarker
                      center={[city.lat, city.lng]}
                      icon={markerIcon}
                      key={idx}
                      className={city.statistic_1}
                    />
                ))}
            </Map>
        </>
    );
}

export default MapCard;
