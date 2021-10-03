import React, { useEffect, useState } from 'react';
import { DeleteOutlined } from '@ant-design/icons';
import { message } from 'antd';
import { BsSearch, VscFilePdf } from 'react-icons/all';
import { useHistory } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import ReportPreview from '../../components/ReportPreview/ReportPreview';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import pdfTemplate from '../../Mocks/pdf';

const colors = {
    red: '#FF120A',
    blue: '#5773FA',
};

const iconSize = '20px';
function getLocalUser() {
    const localUser = localStorage.getItem('user');
    if (localUser) {
        // console.log('user logged in is ', localUser);
        return JSON.parse(localUser);
    }
    return null;
}

const body = pdfTemplate();
const getBackendData = () =>{
    const localUser = getLocalUser();
    // if (localUser && localUser.id === 'b5aa283d-35d1-421d-a8c6-42dd3e115463') {
    //     return [{ data: localUser, isPending: false, error: false }];
    // }

    const [data, setData] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() =>{
        const abortCont = new AbortController();
        const requestObj = {
            id: localUser.id,
        };

        fetch(`${process.env.REACT_APP_BACKEND_HOST}/getAllReportsByUser`,
            {
                signal: abortCont.signal,
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestObj),
            })
            .then((res) => {
                if (!res.ok) {
                    throw Error(res.error);
                }
                return res.json();
            })
            .then((dataObj)=>{
                setData(dataObj);
                setIsPending(false);
                setError(null);
            })
            .catch((err) => {
                if (err.name === 'AbortError') console.log('Fetch Aborted');
                else {
                    // console.log(err.message)
                    setData(body);
                    setError(err.message);
                    setIsPending(false);
                }
            });
        return () => abortCont.abort();
    }, []);
    return { data, isPending, error };
};
const ReportsPage = () => {
    // const user = useRecoilValue(userState);

    const [reports, setReports] = useState(null);
    const [preview, setPreview] = useState(null);
    const [searchKey, setSearchKey] = useState('');
    const history = useHistory();
    const [showDeletePopup, setShowDeletePopup] = useState(false);
    const [currentPdf, setCurrentPdf] = useState(null);
    const [document, setDocument] = useState(null);
    const user = getLocalUser();
    const { data, isPending, error } = getBackendData();

    const handleDelete = () => {
        const abortCont = new AbortController();

        const requestObj = {
            reportID: currentPdf.id,
            userID: user.id,
        };

        fetch(`${process.env.REACT_APP_BACKEND_HOST}/deleteUserReportById`,
            {
                signal: abortCont.signal,
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestObj),
            })
            .then((res)=>{
                if (!res.ok) {
                    throw Error(res.error);
                }
                return res.json();
            })
            .then((dataObj) =>{
                closeDeletePopup();
                if (!dataObj.delete) {
                    message.success('successfully deleted');
                } else {
                    message.error('could not delete report');
                }
            }).catch((err) =>{});
        // setReports((prev)=>prev.filter((item)=> item.id !== currentPdf));
        // message.success('Report Deleted');
        // closeDeletePopup();
        setReports((prev)=>prev.filter((item)=> item.id !== requestObj.reportID));
    };

    const handleSearch = (value) => {
        setSearchKey(value);
        if (value.trim() !== '') {
            setReports(data.reports.filter((item) =>{
                return item.title.toLowerCase().includes(value.toLowerCase());
            }));
        } else {
            setReports(data.reports);
        }
    };

    const handlePreview = (id, pdf, popup) =>{
        setCurrentPdf({
            id,
            popup,
            data: pdf,
        });
    };

    useEffect(()=>{
        if (currentPdf !== null) {
            if (currentPdf.popup === 'preview') {
                setPreview(true);
                setShowDeletePopup(false);
            } else if (currentPdf.popup === 'delete') {
                setShowDeletePopup(true);
                setPreview(false);
            }
            // else if (currentPdf.popup === 'share') {
            //
            // }
        }
        }, [currentPdf]);

    const closePreview = () =>{
        setPreview(false);
        setCurrentPdf(null);
    };

    const closeDeletePopup = () =>{
        setShowDeletePopup(false);
        setCurrentPdf(null);
    };
    return (
        <>
            {
                preview
                    ? (
                        <ReportPreview
                          closePopup={closePreview}
                          className="pdf large"
                          title="pdf-preview"
                          currentFile={currentPdf}
                        />
                    ) :
                    null
            }
            {
                showDeletePopup
                    ? (
                        <SimplePopup
                          closePopup={() => closeDeletePopup()}
                          popuptitle="Delete Report"
                          popupID="delete-model-popup"
                        >
                            <div id="delete-model-popup-msg">Are you sure you want to delete this report?</div>
                            <div id="delete-model-popup-btn-container">
                                <button
                                  type="button"
                                  id="delete-model-popup-btn-yes"
                                  onClick={() => handleDelete()}
                                >
                                    Yes
                                </button>
                                <button
                                  type="button"
                                  id="delete-model-popup-btn-no"
                                  onClick={() => closeDeletePopup()}
                                >
                                    No
                                </button>
                            </div>
                        </SimplePopup>
                    ) :
                    null
            }
            <div className="default-page-container">
                <SideBar currentPage="5" />

                {data && reports === null && setReports(data)}

                <div className="reports-content-section">
                    <div className="content-page-title ">Reports</div>
                    <div className="search-bar-container">
                        <BsSearch className=" float-middle clickable" onClick={() => handleSearch(searchKey)} />
                        <input
                          className="search-bar input field"
                          type="text"
                          placeholder="search"
                          name="searchbar"
                          id="searchbar"
                          value={searchKey}
                          onChange={(event)=>handleSearch(event.target.value)}
                        />
                    </div>
                    {
                        reports &&
                        (
                            <div className="reports-content-grid">
                                {reports.map((report) => (
                                    <div className="report-card" key={report.id}>
                                        <VscFilePdf className="icon clickable" style={{ fontSize: iconSize }} onClick={()=>handlePreview(report.id, report.pdf, 'preview')} />
                                        <div className="text-container">
                                            <div className="report-title clickable" onClick={()=>handlePreview(report.id, report.pdf, 'preview')}>{report.name}</div>
                                            <div className="report-date clickable" onClick={()=>handlePreview(report.id, report.pdf, 'preview')}>{report.date}</div>
                                        </div>
                                        <DeleteOutlined onClick={()=>handlePreview(report.id, null, 'delete')} style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                                    </div>
                                    ),
                                )}
                            </div>
                        )
                    }
                </div>
            </div>
        </>
    );
};

export default ReportsPage;
