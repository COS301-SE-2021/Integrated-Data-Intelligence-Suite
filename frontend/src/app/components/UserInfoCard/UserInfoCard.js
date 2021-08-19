import {Button, Card, Dropdown, Menu, Skeleton, Avatar } from "antd";
// import Avatar from "antd/es/avatar/avatar";
import {UpOutlined, CaretUpFilled} from "@ant-design/icons";
import React, {Component} from 'react';
import ExitMenuTooltip from "../ExitMenuTooltip/ExitMenuTooltip";
const { Meta } = Card;

function setUserName(){
    const localUser = localStorage.getItem("user");
    if(localUser){
        return JSON.parse(localUser);
    }else{
        return "user";
    }
}

class UserInfoCard extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: setUserName()
        };
    }



    render() {
        const {loading, user} = this.state;

        return (
            <>
                {/*<Switch checked={!loading} onChange={this.onChange} style={{width: 40}}/>*/}
                <Card id={'user_avatar_card'}>
                    <Skeleton loading={false} avatar active>

                        {/*The drop down menu that allows the user to log out or lock the app*/}
                        <Dropdown
                            overlay={<ExitMenuTooltip/>}
                            placement="bottomLeft"
                            arrow={true}
                            trigger={'click'}
                            className={"exit_menu_dropdown"}
                        >
                            <Button
                                id={'exit_menu_button'}
                                icon={
                                    <CaretUpFilled
                                        className={'exit_menu_ellipsis_icon'}
                                    />
                                }
                            >
                            </Button>
                        </Dropdown>

                        {/*The user Avatar Image*/}
                        <Meta
                            id={'meta_id'}
                            className={'user_meta_card'}
                            title={user.username}
                            // description={'name@example.com'}
                        />
                    </Skeleton>
                </Card>
            </>
        );
    }
}

export default UserInfoCard;
