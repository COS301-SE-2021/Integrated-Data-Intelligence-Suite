import {Button} from 'antd';
import React, {Component} from 'react';
import "../../../styles/VerifyPage/resendButton.css";

class ResendButton extends React.Component {
    constructor(props) {
        super(props);
    }

    state = {
        is_loading: false,
        email: localStorage.getItem("email")
    }

    render() {
        return (
            <>
                <Button
                    type="link"
                    htmlType="submit"
                    className="link-button"
                >
                    Resend code
                </Button>
            </>
        );
    }
}

export default ResendButton;