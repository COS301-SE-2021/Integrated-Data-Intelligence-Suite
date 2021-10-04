import React, { PureComponent } from 'react';
import { GrClose } from 'react-icons/all';

class ReportPreview extends PureComponent {
    componentDidMount() {
        if (this.props.currentFile.id && this.props.userID) {
            const abortCont = new AbortController();

            const requestObj = {
                reportID: this.props.currentFile.id,
                userID: this.props.userID,
            };

            fetch(`${process.env.REACT_APP_BACKEND_HOST}/generateReport`,
                {
                    signal: abortCont.signal,
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(requestObj),
                })
                .then((res) =>{
                    if (res.ok) {
                        throw Error(res.error);
                    }
                    return res.json();
                })
                .catch((err) => {
                    if (err.name === 'AbortError') console.log('Fetch Aborted');
                });
        }
    }

    render() {
        const {
            closePopup,
            className,
            title,
            currentFile,
        } = this.props;
        // console.log('pdf is ');
        // console.log({ currentFile });
        // console.log(<iframe title="myframe" src="http://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pdf" />);
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
                          id="pdf-frame"
                          title="pdf=preview"
                          src={`data:application/pdf;base64,${currentFile.data || currentFile.pdf}`}
                          frameBorder="10"
                          scrolling="auto"
                          height="1000vh"
                          width="800vw"
                        />
                    </div>
                </div>
            </div>

        );
    }
}

export default ReportPreview;
