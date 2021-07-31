import {Button, Card, Dropdown, Menu, Skeleton} from "antd";
import Meta from "antd/es/card/Meta";
import Avatar from "antd/es/avatar/avatar";
import {EllipsisOutlined} from "@ant-design/icons";
import React, {Component} from 'react';
import ExitMenuTooltip from "./ExitMenuTooltip";

class UserInfoCard extends React.Component {
    state = {
        loading: true,
    };

    onChange = checked => {
        this.setState({loading: !checked});
    };

    render() {
        const {loading} = this.state;

        return (
            <>
                {/*<Switch checked={!loading} onChange={this.onChange} style={{width: 40}}/>*/}
                <Card id={'user_avatar_card'}>
                    <Skeleton loading={false} avatar active>
                        <Meta
                            id={'meta_id'}
                            className={'user_meta_card'}
                            title="Myron Lopes"
                            avatar={
                                <Avatar id={'user_avatar_pic'}
                                        shape={'round'}>
                                    M
                                </Avatar>
                            }
                        />
                        <Dropdown
                            overlay={<ExitMenuTooltip/>}
                            placement="topCenter"
                            arrow={true}
                            trigger={'click'}
                        >
                            <Button id={'exit_menu_button'}
                                    icon={
                                        <EllipsisOutlined
                                            className={'exit_menu_ellipsis_icon'}
                                            // style={{fontSize: '25px'}}
                                        />
                                    }
                            >
                            </Button>
                        </Dropdown>
                    </Skeleton>
                </Card>
            </>
        );
    }
}

export default UserInfoCard;
