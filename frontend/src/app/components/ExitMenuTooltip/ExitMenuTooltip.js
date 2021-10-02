import React from 'react';
import { Menu } from 'antd';
import './ExitMenuTooltip.css';
import { Link } from 'react-router-dom';

function ExitMenuTooltip() {
    return (
        <>
            <Menu id="exit_menu">
                <Menu.Item key="2">
                    <Link className="logout_link" to="/login">logout</Link>
                </Menu.Item>
            </Menu>

        </>
    );
}

export default ExitMenuTooltip;
