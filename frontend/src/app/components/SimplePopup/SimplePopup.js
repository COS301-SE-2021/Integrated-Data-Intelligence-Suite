import React from 'react';
import './SimplePopup.css';

export default class SimplePopup extends React.Component {
    handlePopup = () => {
        this.props.closePopup();
    };

    render() {
        return (
            <>
                <div className={'popup-container'}>
                    <div className={'popup-div'}>
                        <div className={'popup-title-bar'}>
                            <div className={'popup-title'}>
                                Some Title
                            </div>
                            <button
                                onClick={this.handlePopup}
                                className={'popup-close-btn'}
                            >
                                X
                            </button>
                        </div>
                        <div className={'popup-body'}>
                            {this.props.children}
                        </div>
                    </div>
                </div>
            </>
        );
    }
}
