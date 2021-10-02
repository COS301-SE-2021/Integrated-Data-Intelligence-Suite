import React, { useState } from 'react';
import { DeleteOutlined } from '@ant-design/icons';
import { message, Popconfirm } from 'antd';
import { BsSearch, VscFilePdf } from 'react-icons/all';
import SideBar from '../../components/SideBar/SideBar';
import ReportPreview from '../../components/ReportPreview/ReportPreview';

const colors = {
    red: '#FF120A',
    blue: '#5773FA',
};

const iconSize = '20px';

const getUserReports = (url) => {
    // TODO function in DataSourceList.js
    // const data = {
    //     reports: [
    //         { id: 1, title: 'Report 1', date: '2021-04-14' },
    //         { id: 2, title: 'Report 2', date: '2021-04-14' },
    //         { id: 3, title: 'Report 3', date: '2021-04-14' },
    //     ],
    // };
    let isPending = false;
    const error = null;

    if (url === 'start') {
        isPending = true;
    }
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

const ReportsPage = () => {
    const [reports, setReports] = useState(null);
    const [preview, setPreview] = useState(null);
    const [searchKey, setSearchKey] = useState('');

    const { data } = getUserReports('/getUserReports');

    const handleDelete = (reportId) => {
        setReports((prev)=>prev.filter((item)=> item.id !== reportId));
        message.success('Report Deleted');
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

    const togglePdfPreview = () =>{
        setPreview(!preview);
    };
    return (
        <>
            {
                preview
                    ? (
                        <ReportPreview
                          closePopup={togglePdfPreview}
                          className="pdf large"
                          title="pdf-preview"
                        />
                    ) :
                    null
            }
            <div className="default-page-container">
                <SideBar currentPage="5" />
                {data && reports === null && setReports(data.reports)}
                {
                    reports &&
                    (
                        <div className="reports-content-section">
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
                            <div className="reports-content-grid">
                                {reports.map((report) => (
                                    <div className="report-card" key={report.id}>
                                        <VscFilePdf className="icon clickable" style={{ fontSize: iconSize }} onClick={()=>setPreview(true)} />
                                        <div className="text-container">
                                            <div className="report-title clickable" onClick={()=>setPreview(true)}>{report.title}</div>
                                            <div className="report-date clickable" onClick={()=>setPreview(true)}>{report.date}</div>
                                        </div>
                                        <Popconfirm
                                          title="Are you sure to delete this item?"
                                          onConfirm={()=>handleDelete(report.id)}
                                          onCancel={()=>{}}
                                          okText="Yes"
                                          cancelText="No"
                                        >
                                            <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                                        </Popconfirm>
                                    </div>
                                ),
                                )}
                            </div>
                        </div>
                    )
                }
            </div>
        </>
    );
};

export default ReportsPage;
