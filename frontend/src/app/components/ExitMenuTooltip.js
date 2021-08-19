import React, {Component} from "react";
import {Menu} from "antd";
import {LockOutlined, PoweroffOutlined} from "@ant-design/icons";
import {
    BrowserRouter as Router,
    Switch,
    Route, Link
} from "react-router-dom";

class ExitMenuTooltip extends React.Component {
    render() {
        return (
            <>
                <Router>
                    <Menu id={'exit_menu'}>
                        <Menu.Item key="1" icon={<LockOutlined/>}>
                            Lock
                        </Menu.Item>
                        <Menu.Item key="2" icon={<PoweroffOutlined/>}>
                            <Link to="/login">logout</Link>
                        </Menu.Item>
                    </Menu>
                </Router>
            </>
        );
    }
}

export default ExitMenuTooltip;