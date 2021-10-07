import { Button } from 'antd';
import React, { Component } from 'react';
import '../VerifyPage/resendButton.css';

class SendOTPButton extends React.Component {
    constructor(props) {
        super(props);
    }

    state = {
        is_loading: false,
        email: '',
    }

    render() {
        return (
            <>
                <Button
                  type="primary"
                  htmlType="submit"
                  className="login_button"
                >
                    Send OTP
                </Button>
            </>
        );
    }
}

export default SendOTPButton;
