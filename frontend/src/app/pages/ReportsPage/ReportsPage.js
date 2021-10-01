import React, { useState } from 'react';
import { DeleteOutlined, DeleteTwoTone, EditTwoTone } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { message, Popconfirm } from 'antd';
import { AiOutlineRight, BsSearch, VscFilePdf } from 'react-icons/all';
import SideBar from '../../components/SideBar/SideBar';
import SimpleSection from '../../components/SimpleSection/SimpleSection';
import SearchBar from '../../components/SearchBar/SearchBar';
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

const ReportsPage = () => {
    const [reports, setReports] = useState(null);
    const [preview, setPreview] = useState(null);
    const [searchKey, setSearchKey] = useState('');

    const { data, isPending, error } = getUserReports('/getUserReports');

    const handleDelete = (reportId) => {
        setReports((prev)=>prev.filter((item)=> item.id !== reportId));
        message.success('Report Deleted');
    };

    const handleSearch = (value) => {
        setSearchKey(value);
        if (value.trim() !== '') {
            setReports(data.reports.filter((item) =>{
                console.log(value);
                return item.title.toLowerCase().includes(value.toLowerCase());
            }));
        } else {
            setReports(data.reports);
        }
    };
    return (
        <div className="default-page-container">
            <SideBar currentPage="5" />
            {data && reports === null && setReports(data.reports)}
            {
                preview === null && reports &&
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
                                    <VscFilePdf className="icon clickable" style={{ fontSize: '40px' }} onClick={()=>setPreview(true)} />
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
            { preview && (
                <div className="main-preview-container">
                    <div><AiOutlineRight className="clickable" style={{ fontSize: '40px', color: colors.red }} onClick={()=> setPreview(null)} /></div>
                    <div>
                        <iframe
                          title="pdf=preview"
                          src="http://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pdf"
                          frameBorder="10px"
                          scrolling="auto"
                          height="100%"
                          width="100%"
                        />
                    </div>
                </div>
            ) }
        </div>
    );
};

export default ReportsPage;
