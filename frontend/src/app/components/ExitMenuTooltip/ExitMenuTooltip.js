import React, {Component} from "react";
import {Menu} from "antd";
import {LockOutlined, PoweroffOutlined} from "@ant-design/icons";
import './ExitMenuTooltip.css';
import {
    Switch,
    Route, Link
} from "react-router-dom";

class ExitMenuTooltip extends React.Component {
    render() {
        return (
            <>
                <Menu id={'exit_menu'}>
                    <Menu.Item key="2" icon={<PoweroffOutlined/>}>
                        <Link className={'logout_link'} to="/login">logout</Link>
                    </Menu.Item>
                </Menu>

            </>
        );
    }
}

export default ExitMenuTooltip;