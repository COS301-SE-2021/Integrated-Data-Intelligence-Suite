import React, {Component}  from "react";
import {Menu} from "antd";
import {LockOutlined, PoweroffOutlined} from "@ant-design/icons";

class ExitMenuTooltip extends React.Component{
    render() {
        return (
            <Menu id={'exit_menu'}>
                <Menu.Item key="1" icon={<LockOutlined/>}>
                    Lock
                </Menu.Item>
                <Menu.Item key="2" icon={<PoweroffOutlined/>}>
                    Logout
                </Menu.Item>
            </Menu>
        );
    }
}

export default ExitMenuTooltip;