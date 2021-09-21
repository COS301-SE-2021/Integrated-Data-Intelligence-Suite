import {Button} from 'antd';
import React, {Component} from 'react';

class VerifyButton extends React.Component {
    constructor(props) {
        super(props);
    }

    state = {
        is_loading: false,
        email: '',
        verificationCode: '',
    }

    render() {
        return (
            <>
                <Button
                    type="primary"
                    htmlType="submit"
                    className="login_button"
                >
                    Verify
                </Button>
            </>
        );
    }
}

export default VerifyButton;