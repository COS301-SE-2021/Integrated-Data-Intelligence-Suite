import React, {Component} from 'react';
import {Card} from "antd";


class DetailsCard extends React.Component {

    state = {}

    render() {
        return (
            <>
                <Card
                    id={'details_card'}
                    title="Details"
                    extra={<p>Tooltip</p>}
                >


                </Card>
            </>
        );
    }
}

export default DetailsCard;