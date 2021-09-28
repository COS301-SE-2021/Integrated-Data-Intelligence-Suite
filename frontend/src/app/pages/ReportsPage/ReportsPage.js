import React, { useState } from 'react';
import { DeleteTwoTone, EditTwoTone } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { message, Popconfirm } from 'antd';

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
    return {data, isPending, error};
};

const ReportsPage = () => {
    const [reports, setReports] = useState(null);

    const { data, isPending, error } = getUserReports('/getUserReports');

    const handleDelete = (reportId) => {
      message.success('Report Deleted');
    };
    return (
        <div className="settings-component">
            {data && reports === null && setReports(data.reports)}
            {reports !== null && reports.map((report, index) => (
                <div>
                    <div className="settings-list-item" key={`report ${report.id}`}>
                        <p className="list-item-title">{report.title}</p>
                        <div className="options-container">
                            <Link className="standard-button" to={`/reports/${report.id}`}>
                                <EditTwoTone twoToneColor={colors.blue} style={{ fontSize: iconSize, padding: '10px' }} />
                            </Link>
                            <Popconfirm
                                title="Are you sure to delete this item?"
                                onConfirm={()=>handleDelete(15)}
                                onCancel={()=>{}}
                                okText="Yes"
                                cancelText="No"
                            >
                                <DeleteTwoTone twoToneColor={colors.red} style={{ fontSize: iconSize, padding: '10px' }} />
                            </Popconfirm>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ReportsPage;
