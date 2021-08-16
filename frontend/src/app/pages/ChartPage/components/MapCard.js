import React, {useEffect, useRef, useState} from 'react';
import 'leaflet/dist/leaflet.css';
import ScriptTag from 'react-script-tag';
import L, {map} from "leaflet";
import "leaflet/dist/leaflet.css";
import "leaflet-draw/dist/leaflet.draw.css";
import '../../../components/leaflet/leaflet.css';
import 'leaflet-snap/leaflet.snap.js';
import datapoints from "../resources/datapoints.json"
import {showCircleData} from "../functions/showCircleData";

//Do not Change the order of these lines
//The Css MUST be loaded before the js
import "leaflet-geometryutil/src/leaflet.geometryutil.js";
import "leaflet-draw/dist/leaflet.draw.js";

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

function MapCard() {
    const [mapLayers, setMapLayers] = useState([]);

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
    let poly;
    // return (
    //     <>
    //         <Card
    //             id={'map_card'}
    //             title="Map Card Title"
    //             extra={<p></p>}
    //         >
    //             {/*<p>Card content</p>*/}
    //             <Map
    //                 id={'map_container_div'}
    //                 center={pretoria_position}
    //                 zoom={9}
    //                 scrollWheelZoom={true}
    //                 ref={map => this.map = Map}
    //             >
    //                 <TileLayer
    //                     attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    //                     url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
    //                 />
    //
    //                 <ScaleControl position={"bottomleft"}/>
    //                 <FeatureGroup>
    //                     <EditControl
    //                         position="topleft"
    //                         onCreated={_onCreate}
    //                         onEdited={_onEdited}
    //                         onDeleted={_onDeleted}
    //                         draw={{}}
    //                     />
    //                 </FeatureGroup>
    //
    //                 {/*Map City markers from cities.json*/}
    //                 {cities.map((city, idx) => (
    //                     <Marker
    //                         position={[city.lat, city.lng]}
    //                         icon={markerIcon}
    //                         key={idx}
    //                     >
    //                         <Popup>
    //                             <b>
    //                                 {city.city}, {city.country}
    //                             </b>
    //                         </Popup>
    //                     </Marker>
    //                     ))}
    //
    //
    //             </Map>
    //
    //             <pre className="text-left">{JSON.stringify(mapLayers)}</pre>
    //         </Card>
    //
    //     </>
    // );

    let osm = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; 2013 OpenStreetMap contributors',
    });

    useEffect(() => {
        var map;
        var osm = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: 'Map data &copy; 2013 OpenStreetMap contributors',
        });

        //Checking if the map already exists? if it does clear it
        //Solves map errors that occur on page reload
        var container = L.DomUtil.get('map_container_div');
        if (container != null) {
            container._leaflet_id = null;
        }

        //Creating the actual map component
        map = L.map('map_container_div', {drawControl: false, dragging: true})
            .setView([48.49, 1.4], 16)
            .addLayer(osm);


        //Adding the edit options to the Draw toolbar
        // FeatureGroup is to store editable layers
        const layer_with_drawn_items = new L.FeatureGroup();
        map.addLayer(layer_with_drawn_items);
        var drawControl = new L.Control.Draw({
            /*
                * enables/disables draw components
                * true = enabled
                * false = disabled
             */
            draw:{
                polygon: false,
                circle: false,
                rectangle: false,
                circlemarker:false,
                marker:true,
                polyline:false
            },


            //Events
            create: {
                featureGroup: layer_with_drawn_items
            },

            edit: {
                featureGroup: layer_with_drawn_items,
                edit: false //Disabling the edit button
            },

            delete: {
                featureGroup: layer_with_drawn_items
            }
        });
        map.addControl(drawControl);

        /*
            * runs everytime a vector is drawn from the toolbar
        */
        map.on(L.Draw.Event.CREATED, function (e) {
            //Everytime a layer is draw on the map
            //add it to the map
            var layer_type = e.layerType,
                layer = e.layer;

            if (layer_type === 'marker') {
                layer.bindPopup('A popup!');
            }

            layer_with_drawn_items.addLayer(layer);

            //store co-ordinates in DB
            console.log(e);

            // const {layerType, layer} = e;
            if (layer_type === "polygon") {
                const {_leaflet_id} = layer;

                setMapLayers((layers) => [
                    ...layers,
                    {id: _leaflet_id, latlngs: layer.getLatLngs()[0]},
                ]);
            }

            //output
            console.log('on create');
            console.log(JSON.stringify(mapLayers));
        });

        map.on(L.Draw.Event.EDITED, function (e) {
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
        });

        map.on(L.Draw.Event.DELETED, function (e) {
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

        });


        /*
            * Retrieving data from a datapoint.json file and
               displaying those data points on the map
        */
        function createCircle(datapoint) {
            console.log("some datapoint value:" + datapoint.lat);
            return L.circle(L.latLng(datapoint.lat, datapoint.lng), {
                className: datapoint.classname,
                id: "broski",
                radius: 69
            });
        }

        function addCircleLayer(some_circle_layer) {
            layer_with_drawn_items.addLayer(some_circle_layer);
        }

        console.log(datapoints);
        var array_of_circle_markers = datapoints.map(createCircle);
        console.log(array_of_circle_markers);
        array_of_circle_markers.forEach(addCircleLayer)


        /*
            * Updating statistics based on circle click
        */
        layer_with_drawn_items.on("click", function (e) {
            var clickedCircle = e.layer; // e.target is the group itself.
            console.log(clickedCircle.options.className);
            console.log();
            console.log(e);
            alert("circle clicked");
            showCircleData(clickedCircle.options.className);
        });
    })

    return (
        <div id="map_container_div">

        </div>
    );


}

export default MapCard;
