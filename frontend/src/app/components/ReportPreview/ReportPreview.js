import React, { PureComponent } from 'react';
import { GrClose } from 'react-icons/all';

class ReportPreview extends PureComponent {
    render() {
        const {
            closePopup,
            className,
            title,
        } = this.props;
        return (
            <div className={`popup-container ${className}`}>
                <div className="main-preview-container popup-body">
                    <div className="popup-title-bar">
                        <div className="popup-title">
                            {title || 'Popup'}
                        </div>
                        <GrClose className="clickable" style={{ fontSize: '30px' }} onClick={closePopup} />
                    </div>
                    <div className="children-div loader">
                        <iframe
                          title="pdf=preview"
                          src="http://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pdf"
                          frameBorder="10"
                          scrolling="auto"
                          height="200px"
                          width="1000px"
                        />
                    </div>
                </div>
            </div>

        );
    }
}

export default ReportPreview;
