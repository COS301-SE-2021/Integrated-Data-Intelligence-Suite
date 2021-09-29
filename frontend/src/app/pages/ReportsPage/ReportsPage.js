import React, { useState } from 'react';
import { DeleteOutlined, DeleteTwoTone, EditTwoTone } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { message, Popconfirm } from 'antd';
import { VscFilePdf } from 'react-icons/all';
import SideBar from '../../components/SideBar/SideBar';
import SimpleSection from '../../components/SimpleSection/SimpleSection';
import SearchBar from '../../components/SearchBar/SearchBar';

const colors = {
    red: '#FF120A',
    blue: '#5773FA',
};

const iconSize = '20px';

const getUserReports = (url) => {
    // TODO function in DataSourceList.js
    const data = {
        reports: [
            { id: 1, title: 'Report 1' },
            { id: 2, title: 'Report 2' },
            { id: 3, title: 'Report 3' },
        ],
    };
    const isPending = false;
    const error = null;
    return { data, isPending, error };
};

const ReportsPage = () => {
    const [reports, setReports] = useState(null);

    const { data, isPending, error } = getUserReports('/getUserReports');

    const handleDelete = (reportId) => {
      message.success('Report Deleted');
    };
    return (
        <div className="default-page-container">
            <SideBar currentPage="5" />
            <div className="reports-content-section">
                <div>Searchbar</div>
                <div className="reports-content-grid">
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title xx</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title try</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">some really long report title that should be truncated</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title well maybe</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title 2</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                    <div className="report-card">
                        <VscFilePdf style={{ fontSize: '40px' }} />
                        <div className="text-container">
                            <div className="report-title">report title working from what</div>
                            <div className="report-date">12-05-2021</div>
                        </div>
                        <DeleteOutlined style={{ color: colors.red, marginTop: '0', cursor: 'pointer' }} />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ReportsPage;
