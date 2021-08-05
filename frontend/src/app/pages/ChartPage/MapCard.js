import React, {Component} from 'react';
import {Card} from "antd";
import {MapContainer, TileLayer, Marker, Popup} from 'react-leaflet'


class MapCard extends React.Component {
    state = {}

    render() {
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
                        center={[ -25.731340, 28.218370]}
                        zoom={13}
                        scrollWheelZoom={false}
                        style={{}}
                    >
                        <TileLayer
                            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        <Marker position={[51.505, -0.09]}>
                            <Popup>
                                A pretty CSS3 popup. <br/> Easily customizable.
                            </Popup>
                        </Marker>
                    </MapContainer>
                </Card>

            </>
        );
    }
}

export default MapCard;
