import React, {Component} from 'react';
import {
    Card,
    Descriptions
} from "antd";


class DetailsCard extends React.Component {
    state = {}

    render() {
        return (
            <>
                <Card
                    id={'details_card'}
                    title="Details Card Title"
                    extra={<p></p>}
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
                            label="Statistic 1"
                        >
                            89%
                        </Descriptions.Item>

                        <Descriptions.Item
                            className={'map_1'}
                            label="Statistic 2"
                        >
                            1810K
                        </Descriptions.Item>

                        <Descriptions.Item
                            className={'map_1'}
                            label="Statistic 3"
                        >
                            Pretoria, Hatfield
                        </Descriptions.Item>
                    </Descriptions>

                </Card>
            </>
        );
    }
}

export default DetailsCard;