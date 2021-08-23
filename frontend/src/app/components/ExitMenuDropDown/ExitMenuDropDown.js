import React, {Component} from 'react';
import {Button, Dropdown} from "antd";
import ExitMenuTooltip from "../ExitMenuTooltip/ExitMenuTooltip";
import {CaretUpFilled} from "@ant-design/icons";

export default function ExitMenuDropDown(){

    return(
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
    );
}


