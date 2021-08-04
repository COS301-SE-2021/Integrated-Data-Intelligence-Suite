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

                    <Descriptions title="User Info" layout="vertical">
                        <Descriptions.Item label="UserName">Zhou Maomao</Descriptions.Item>
                        <Descriptions.Item label="Telephone">1810000000</Descriptions.Item>
                        <Descriptions.Item label="Live">Hangzhou, Zhejiang</Descriptions.Item>
                        <Descriptions.Item label="Address" span={2}>
                            No. 18, Wantang Road, Xihu District, Hangzhou, Zhejiang, China
                        </Descriptions.Item>
                        <Descriptions.Item label="Remark">empty</Descriptions.Item>
                    </Descriptions>,

                </Card>
            </>
        );
    }
}

export default DetailsCard;