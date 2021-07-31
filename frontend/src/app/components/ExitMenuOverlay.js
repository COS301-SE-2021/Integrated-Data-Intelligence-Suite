import React, {Component}  from "react";
import {Menu} from "antd";
import {LockOutlined, PoweroffOutlined} from "@ant-design/icons";

class ExitMenuOverlay extends React.Component{
    render() {
        return (
            <Menu>
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

export default ExitMenuOverlay;