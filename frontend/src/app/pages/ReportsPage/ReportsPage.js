import React, { useEffect, useState } from 'react';
import { DeleteOutlined } from '@ant-design/icons';
import { message } from 'antd';
import { BsSearch, VscFilePdf } from 'react-icons/all';
import SideBar from '../../components/SideBar/SideBar';
import ReportPreview from '../../components/ReportPreview/ReportPreview';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import {useHistory} from "react-router-dom";

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

const body = JSON.parse('[\n    {\n        "pdf": "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ZG9iago2IDAgb2JqCjw8L1R5cGUvUGFnZS9NZWRpYUJveFswIDAgNTk1IDg0Ml0vUmVzb3VyY2VzPDwvRm9udDw8L0YxIDEgMCBSPj4+Pi9Db250ZW50cyA1IDAgUi9QYXJlbnQgMyAwIFI+PgplbmRvYmoKMSAwIG9iago8PC9UeXBlL0ZvbnQvU3VidHlwZS9UeXBlMS9CYXNlRm9udC9IZWx2ZXRpY2EvRW5jb2RpbmcvV2luQW5zaUVuY29kaW5nPj4KZW5kb2JqCjMgMCBvYmoKPDwvVHlwZS9QYWdlcy9Db3VudCAyL0tpZHNbNCAwIFIgNiAwIFJdPj4KZW5kb2JqCjcgMCBvYmoKPDwvVHlwZS9DYXRhbG9nL1BhZ2VzIDMgMCBSPj4KZW5kb2JqCjggMCBvYmoKPDwvUHJvZHVjZXIoaVRleHSuIDUuNS4xMy4yIKkyMDAwLTIwMjAgaVRleHQgR3JvdXAgTlYgXChBR1BMLXZlcnNpb25cKSkvQ3JlYXRpb25EYXRlKEQ6MjAyMTEwMDMwMzI1MjArMDInMDAnKS9Nb2REYXRlKEQ6MjAyMTEwMDMwMzI1MjArMDInMDAnKT4+CmVuZG9iagp4cmVmCjAgOQowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDE5MTcgMDAwMDAgbiAKMDAwMDAwMDAxNSAwMDAwMCBuIAowMDAwMDAyMDA1IDAwMDAwIG4gCjAwMDAwMDEzMjUgMDAwMDAgbiAKMDAwMDAwMTQzNyAwMDAwMCBuIAowMDAwMDAxODA1IDAwMDAwIG4gCjAwMDAwMDIwNjIgMDAwMDAgbiAKMDAwMDAwMjEwNyAwMDAwMCBuIAp0cmFpbGVyCjw8L1NpemUgOS9Sb290IDcgMCBSL0luZm8gOCAwIFIvSUQgWzw3NzQ0ZmVmYjk4YjBiZDg4MDFiMzA0Mjg3NDM4ZDVlMD48Nzc0NGZlZmI5OGIwYmQ4ODAxYjMwNDI4NzQzOGQ1ZTA+XT4+CiVpVGV4dC01LjUuMTMuMgpzdGFydHhyZWYKMjI2NwolJUVPRgo=",\n        "name": "UserReport",\n        "date": "03/10/2021 03:25:20",\n        "id": "5414e9b8-8b65-4f54-b988-67383685cf89",\n        "fallback": false,\n        "fallbackMessage": ""\n    }\n]');
const getUserReports = (url, user) => {
    // TODO function in DataSourceList.js
    // const data = {
    //     reports: [
    //         { id: 1, title: 'Report 1', date: '2021-04-14' },
    //         { id: 2, title: 'Report 2', date: '2021-04-14' },
    //         { id: 3, title: 'Report 3', date: '2021-04-14' },
    //     ],
    // };
    const isPending = false;
    const error = null;
    return {
        data: {
            reports: [
                { id: 1, title: 'Report 1', date: '2021-04-14' },
                { id: 2, title: 'Report 2', date: '2021-04-14' },
                { id: 3, title: 'Report 3', date: '2021-04-14' },
            ],
        },
        isPending,
        error,
    };
};

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
            }).catch((err) =>{})
        //setReports((prev)=>prev.filter((item)=> item.id !== currentPdf));
        //message.success('Report Deleted');
        //closeDeletePopup();
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
