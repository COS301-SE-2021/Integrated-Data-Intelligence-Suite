import React, {Component} from 'react';
import {
    Card,
    Descriptions
} from "antd";

const hideDetailsBar = () =>{
    const detailsbar = document.getElementById("map_card_sidebar")
    detailsbar.style.display="none";
}

class DetailsCard extends React.Component {
    state = {}

    render() {
        return (
            <>
                <Card
                    id={'details_card'}
                    title=""
                    extra={<p onClick={hideDetailsBar}>close</p>}
                >

                    <Descriptions
                        // className={'descriptions_div'}
                        layout="vertical"
                        column={1}
                        bordered={false}
                        colon={false}
                    >
                        <Descriptions.Item
                            className={'map_1'}
                            label="Entity Name"
                        >
                            Individual
                        </Descriptions.Item>

                        <Descriptions.Item
                            className={'map_1'}
                            label="Entity Type"
                        >
                            Strong
                        </Descriptions.Item>

                        <Descriptions.Item
                            className={'map_1'}
                            label="Average Likes"
                        >
                            46776
                        </Descriptions.Item>
                    </Descriptions>

                </Card>
            </>
        );
    }
}

export default DetailsCard;