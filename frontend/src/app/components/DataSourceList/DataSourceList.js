import React, { useEffect, useState } from 'react';
import { Popconfirm, message } from 'antd';
import { Link } from 'react-router-dom';
import { DeleteTwoTone, EditTwoTone } from '@ant-design/icons';

const colors = {
    red: '#FF120A',
    blue: '#5773FA',
};

const iconSize = '20px';

const getAllSources = (url) => {
    const [data, setData] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const abortCont = new AbortController();

        fetch(`${process.env.REACT_APP_BACKEND_HOST}${url}`, { signal: abortCont.signal })
            .then((res) => {
                if (!res.ok) {
                    throw Error(res.error);
                }
                return res.json();
            })
            .then((data) => {
                // console.log('data is here', data);
                if (data.success) {
                    setData(data.sources);
                } else {
                    setData([]);
                }
                setIsPending(false);
                setError(null);
            })
            .catch((err) => {
                if (err.name === 'AbortError') console.log('Fetch Aborted');
                else {
                    // console.log(err.message)
                    setError(err.message);
                    setIsPending(false);
                }
            });

        return () => abortCont.abort();
    }, [url]);
    return { data, isPending, error };
};

const DataSourceList = () => {
    const [sources, setSources] = useState(null);

    const { data, isPending, error } = getAllSources('/getAllSources');

    const handleDelete = (sourceId) =>{
        const requestBody = {
            id: sourceId,
        };
        setSources((prev)=>prev.filter((item)=> item.id !== sourceId));
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody),
        };
        fetch(`${process.env.REACT_APP_BACKEND_HOST}/deleteSource`, requestOptions)
            .then((response) => response.json())
            .then((json) => {
                if (json.success) {
                    message.success(json.message);
                } else {
                    message.error(json.message);
                }
            });
    };

    return (
        <div className="settings-component settings-component">
            <div className="add-source">
                <Link to="/settings/source/new" className="standard-filled button">new Source</Link>
            </div>
            {data && sources === null && setSources(data)}
            {sources !== null && sources.map((source, index) =>(
                <div>
                    <div className="settings-list-item" key={`source ${source.id}${index}`}>
                        <p className="list-item-title">{source.name}</p>
                        <div className="options-container">
                            <Link className="standard button" to={`/settings/source/${source.id}`}>
                                <EditTwoTone twoToneColor={colors.blue} style={{ fontSize: iconSize, padding: '10px' }} />
                            </Link>
                            <Popconfirm
                              title="Are you sure to delete this item?"
                              onConfirm={()=>handleDelete(source.id)}
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

export default DataSourceList;
