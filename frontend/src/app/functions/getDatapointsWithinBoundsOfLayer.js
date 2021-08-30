import L, { map } from 'leaflet';
// import "../components/MapCard/leaflet/leaflet";
// import 'leaflet-geometryutil/src/leaflet.geometryutil.js';
// import 'leaflet-draw/dist/leaflet.draw.js';

const layers = L.layerGroup();

export function getDatapointsWithinBoundsOfLayer(e) {
    const features = [];
    // map.eachLayer(function (layer) {
    //     if (layer instanceof L.Marker) {
    //         if (map.getBounds()
    //             .contains(layer.getLatLng())) {
    //             features.push(layer.feature);
    //         }
    //     }
    // });

    console.log('get-all-datapoints-within-bounds-of-layer()');
    console.log(e);

    console.log();
    console.log('=======Layers within Map=======');
    console.log(layers);
}
