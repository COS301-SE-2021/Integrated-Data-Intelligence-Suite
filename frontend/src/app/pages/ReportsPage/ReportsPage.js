import React, { useEffect, useState } from 'react';
import { DeleteOutlined } from '@ant-design/icons';
import { Button, message } from 'antd';
import {
 BsSearch, ImShare, VscFilePdf,
} from 'react-icons/all';
import { useHistory } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import ReportPreview from '../../components/ReportPreview/ReportPreview';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import pdfTemplate from '../../Mocks/pdf';
import InputBoxWithLabel from '../../components/InputBoxWithLabel/InputBoxWithLabel';
import {useRecoilValue} from "recoil";
import {userState} from "../../assets/AtomStore/AtomStore";

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
const getBackendData = (localUser) =>{
    // const localUser =
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
            .then((data)=>{
                console.log(data);
                if (data.status.toLowerCase() === 'ok') {
                    if (data.data.success) {
                        setData(data.data);
                        setIsPending(false);
                        setError(null);
                    }
                }
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
    const [searchKey, setSearchKey] = useState('');
    const history = useHistory();

    const [preview, setPreview] = useState(null);
    const [showDeletePopup, setShowDeletePopup] = useState(false);
    const [share, setShare] = useState(null);

    const [currentPdf, setCurrentPdf] = useState(null);
    const [pdfdoc, setPdfdoc] = useState(null);

    const [emailLoading, setEmailLoading] = useState(false);

    const user = useRecoilValue(userState);
    const { data, isPending, error } = getBackendData(user);

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
            }).catch((err) =>closeSharePopup());
        // setReports((prev)=>prev.filter((item)=> item.id !== currentPdf));
        // message.success('Report Deleted');
        // closeDeletePopup();
        setReports((prev)=>prev.filter((item)=> item.id !== requestObj.reportID));
    };

    const handleSearch = (value) => {
        setSearchKey(value);
        if (value.trim() !== '') {
            setReports(data.data.reports.filter((item) =>{
                return item.title.toLowerCase().includes(value.toLowerCase());
            }));
        } else {
            setReports(data.data.reports);
        }
    };

    const handleSubmitEmail = () =>{
        setEmailLoading(true);
        const inputBox = document.getElementById('email-input-box');
        const email = inputBox.value;

        const abortCont = new AbortController();

        const requestObj = {
            reportId: currentPdf.id,
            to: email,
        };

        fetch(`${process.env.REACT_APP_BACKEND_HOST}/shareReport`,
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
                // closeDeletePopup();
                setEmailLoading(false);
                if (dataObj.success) {
                    message.success('sent')
                        .then(()=>{
                            closeSharePopup();
                    });
                } else {
                    message.error(dataObj.message)
                        .then(()=>{
                            closeSharePopup();
                        });
                }
            }).catch((err) =>{
                setEmailLoading(false);
                closeSharePopup();
        });
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
            } else if (currentPdf.popup === 'share') {
                setShare(true);
            }
        }
        }, [currentPdf]);

    const closePreview = () =>{
        setPreview(false);
        setCurrentPdf(null);
    };

    const closeSharePopup = () => {
        setShare(false);
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
                share
                    ? (
                        <SimplePopup
                          closePopup={() => closeSharePopup()}
                          popupTitle="Share Model"
                        >
                            <div id="share-model-container">
                                <InputBoxWithLabel
                                  inputLabel="Email"
                                  inputLabelID="email-label"
                                  inputID="email-input-box"
                                  placeholder="email@domain.com"
                                />
                            </div>
                            <Button
                              className="primary-btn"
                              type="button"
                              onClick={handleSubmitEmail}
                              loading={emailLoading}
                            >
                                share
                            </Button>
                        </SimplePopup>
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
                        <BsSearch className=" float-middle clickable pink-icon" style={{ color: '#E80057FF' }} onClick={() => handleSearch(searchKey)} />
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
                                        <VscFilePdf className="icon clickable pink-icon" style={{ fontSize: 36, color: '#E80057FF' }} onClick={()=>handlePreview(report.id, report.pdf, 'preview')} />
                                        <div className="text-container">
                                            <div className="report-title clickable" onClick={()=>handlePreview(report.id, report.pdf, 'preview')}>{report.name}</div>
                                            <div className="report-date clickable" onClick={()=>handlePreview(report.id, report.pdf, 'preview')}>{report.date}</div>
                                        </div>
                                        <div className="report-button-container">
                                            <ImShare
                                              onClick={()=>handlePreview(report.id, null, 'share')}
                                              style={
                                                  {
                                                      fontSize: iconSize,
                                                      color: colors.blue,
                                                      marginTop: '0',
                                                      cursor: 'pointer',
                                                  }}
                                            />
                                            <DeleteOutlined
                                              onClick={()=>handlePreview(report.id, null, 'delete')}
                                              style={{
                                                  fontSize: iconSize,
                                                  color: colors.red,
                                                  marginTop: '0',
                                                  cursor: 'pointer',
                                              }}
                                            />
                                        </div>
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
