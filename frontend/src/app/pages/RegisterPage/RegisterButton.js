import {Button} from 'antd';
import React, {Component} from 'react';
// import {validateLoginDetails} from "./functions/validateLoginDetails";

class RegisterButton extends React.Component {
    constructor(props) {
        super(props);
    }

    state = {
        is_loading: false,
        username: '',
        password: '',
    }

    enterLoading(val) {

        this.setState({
            is_loading: !this.state.is_loading,
        })

        this.setState({
            is_loading: false,
        })

        // console.log(user)
    }

    render() {
        return (
            <>
                <Button
                    type="primary"
                    htmlType="submit"
                    className="login_button"
                    loading={this.state.is_loading}
                    onClick={() => this.enterLoading(!this.state.is_loading)}
                >
                    Register
                </Button>
            </>
        );
    }
}

export default RegisterButton;