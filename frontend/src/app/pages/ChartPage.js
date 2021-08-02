import React, {Component} from 'react';
import SideBar from "../components/SideBar/SideBar";
import {
    Input,
    Layout,
    Card,
    Typography
} from 'antd';
import {Route, Switch} from "react-router-dom";
import {MapContainer, TileLayer, Marker, Popup} from 'react-leaflet'
import 'leaflet/dist/leaflet.css';


const {Title, Text} = Typography;
const {Header, Footer, Sider, Content} = Layout;


class ChartPage extends Component {
    state = {}

    render() {
        return (
            <>
                <Switch>
                    <Route exact path='/chart'>
                        <Layout id={'outer_layout'} className={'chart-page'}>
                            <SideBar/>
                            <Layout id={'inner_layout_div'}>
                                <Header id={'top_bar'}>
                                    <Title level={1}>Chart Page Title</Title>
                                </Header>

                                <Content id={'content_section'}>
                                    <Card title="Default size card"
                                          extra={<p>Tooltip</p>}
                                          id={'map_card'}
                                    >
                                        <p>Card content</p>
                                        <MapContainer
                                            center={[51.505, -0.09]}
                                            zoom={13}
                                            scrollWheelZoom={false}
                                            style={{
                                                height: 300,
                                                width: 300,
                                                border: '3px solid yellow'
                                            }}
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

                                </Content>

                            </Layout>
                        </Layout>
                    </Route>
                </Switch>
            </>
        );
    }

}

export default ChartPage;
