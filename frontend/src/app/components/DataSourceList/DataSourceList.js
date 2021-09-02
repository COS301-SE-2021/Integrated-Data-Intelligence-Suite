import React from 'react';
import { Divider, Popconfirm, message } from 'antd';
import { Link, Redirect } from 'react-router-dom';
import { DeleteTwoTone, EditTwoTone } from '@ant-design/icons';

const colors = {
    red: '#FF120A',
    blue: '#5773FA',
};

const iconSize = '20px';

const DataSourceList = () => {
    // const { sources } = props;

    const sources = [
        {
            id: 1,
            name: 'source1',
            parameters: [
                { key: 'key1', value: 'value1' },
                { key: 'key2', value: 'value2' },
                { key: 'key3', value: 'value3' },
                { key: 'key4', value: 'value4' },
                { key: 'key5', value: 'value5' },
                { key: 'key6', value: 'value6' },
                { key: 'key7', value: 'value7' },
            ],
        },
        {
            id: 2,
            name: 'source2',
            parameters: [
                { key: 'key1', value: 'value1' },
                { key: 'key2', value: 'value2' },
                { key: 'key3', value: 'value3' },
                { key: 'key4', value: 'value4' },
                { key: 'key5', value: 'value5' },
                { key: 'key6', value: 'value6' },
                { key: 'key7', value: 'value7' },
            ],
        },
        {
            id: 3,
            name: 'source3',
            parameters: [
                { key: 'key1', value: 'value1' },
                { key: 'key2', value: 'value2' },
                { key: 'key3', value: 'value3' },
                { key: 'key4', value: 'value4' },
                { key: 'key5', value: 'value5' },
                { key: 'key6', value: 'value6' },
                { key: 'key7', value: 'value7' },
            ],
        },
    ];
    const deleteSource = (sourceId) => {
        alert(`source ${sourceId} deleted`);
    };

    // const handleDelete = (dataSource) =>{
    //     confirmAlert({
    //         title: 'Confirm delete',
    //         message: `Delete ${dataSource.name}?`,
    //         buttons: [
    //             {
    //                 label: 'Yes',
    //                 onClick: () => null,
    //             },
    //             {
    //                 label: 'No',
    //                 onClick: () => deleteSource(dataSource.id),
    //             },
    //         ],
    //     });
    // };

    const handleDelete = (sourceId) =>{
        message.success(`deleted ${sourceId}`);
    };

    return (
        <div className="source-list">
            <div className="add-source">
                <Link to="/settings/source/new" className="standard-filled button">new Source</Link>
            </div>
            { sources.map((source) =>(
                <div>
                    <div className="source-preview" key={source.id}>
                        <p className="source-title">{source.name}</p>
                        <div className="button-div">
                            <Link className="standard button" to={`/settings/source/${source.id}`}><EditTwoTone twoToneColor={colors.blue} style={{ fontSize: iconSize, padding: '10px' }} /></Link>
                            <Popconfirm
                              title="Are you sure to delete this task?"
                              onConfirm={()=>handleDelete(source.id)}
                              onCancel={()=>{}}
                              okText="Yes"
                              cancelText="No"
                            >
                                <DeleteTwoTone twoToneColor={colors.red} style={{ fontSize: iconSize, padding: '10px' }} />
                            </Popconfirm>
                        </div>
                    </div>
                    {/* <Divider/> */}
                </div>
            ))}
        </div>
    );
};

export default DataSourceList;
