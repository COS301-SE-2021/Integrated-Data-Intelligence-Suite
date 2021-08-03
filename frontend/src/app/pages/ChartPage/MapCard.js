import React, {Component} from 'react';
import {Card} from "antd";
import {MapContainer, TileLayer, Marker, Popup} from 'react-leaflet'


class MapCard extends React.Component {
    state = {}

    render() {
        return (
            <>
                <Card
                    title="Default size card"
                    extra={<p>Tooltip</p>}
                    id={'map_card'}
                >
                    <p>Card content</p>
                    <MapContainer
                        id={'map_container_div'}
                        center={[51.505, -0.09]}
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
