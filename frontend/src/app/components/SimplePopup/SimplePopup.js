import React from 'react';
import { GrClose } from 'react-icons/all';
import './SimplePopup.css';

export default class SimplePopup extends React.Component {
    render() {
        const {
            closePopup,
            children,
        } = this.props;
        return (
            <>
                <div className="popup-container">
                    <div
                        className={`popup-div ${this.props.popupExtraClassNames}`}
                        id={this.props.popupID}
                    >
                        <div className="popup-title-bar">
                            <div className="popup-title">
                                {this.props.popupTitle}
                            </div>
                            <GrClose
                                className="clickable"
                                style={{ fontSize: '24px' }}
                                onClick={closePopup}
                            />
                        </div>
                        <div className="popup-body">
                            {children}
                        </div>
                    </div>
                </div>
            </>
        );
    }
}
