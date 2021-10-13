import React, { useEffect, useState } from 'react';
import { RiAddLine, VscFilePdf } from 'react-icons/all';
import { useRecoilValue } from 'recoil';
import { message } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';
import SideBar from '../../components/SideBar/SideBar';
import { userState } from '../../assets/AtomStore/AtomStore';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import useGet from '../../functions/useGet';
import UserPermissions from '../UserPermissionsPage/UserPermissions';
import AddDataSource from '../AddDataSourcePage/AddDataSource';

const iconSize = '20px';
const colors = {
    red: '#ffffff',
};
const skeleton = {
    id: null,
    name: '',
    method: 'GET',
    url: '',
    searchKey: '',
    authType: 'none',
    authorization: '',
    parameters: [],
    date: '',
    interactions: '',
    collections: '',
    location: '',
};

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

const ManageSourcesPage = () => {
    const localUser = useRecoilValue(userState);
    const [dataSources, setDataSources] = useState(null);

    const [currentSource, setCurrentSource] = useState(null);
    const [showSource, setShowSource] = useState(false);

    // const { data, isPending, error } = getAllSources('/getAllSources');
    const { data, isPending, error } = useGet('/getAllSources');

    function handlePreview(id) {
        setCurrentSource(id);
    }

    function closePopup() {
        setCurrentSource(null);
        setShowSource(false);
    }

    useEffect(() =>{
        if (currentSource !== null) {
            setShowSource(true);
        }
    }, [currentSource]);

    const handleDelete = (sourceId) =>{
        const requestBody = {
            id: sourceId,
        };
        setDataSources((prev)=>prev.filter((item)=> item.id !== sourceId));
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

    function extractData(data) {
        console.log(data);
        if (data && data.status.toLowerCase() === 'ok') {
            if (data.data.success) {
                setDataSources(data.data.sources);
                return true;
            }
        }
        setDataSources([]);
        return true;
    }

    function addDataSource() {
        const source = {
            id: null,
            name: '',
            method: 'GET',
            url: '',
            searchKey: '',
            authType: 'none',
            authorization: '',
            parameters: [],
        };
        setCurrentSource(source);
    }

    return (
        <>
            {data && dataSources === null && extractData(data)}
            {
                showSource
                    ? (
                        <SimplePopup
                          closePopup={closePopup}
                          popupTitle="Manage User"
                        >
                            <AddDataSource dataSource={currentSource} />
                        </SimplePopup>
                    ) :
                    null
            }
            <div className="default-page-container">
                <SideBar currentPage="9" />
                <div className="reports-content-section">
                    <div className="content-page-title ">Manage Data Sources</div>
                    <button
                      className="simple-btn simple-btn-hover"
                      onClick={() => handlePreview(skeleton)}
                    >
                        <RiAddLine className="simple-btn-icon" />
                        Add Model
                    </button>

                    {
                        dataSources &&
                        (
                            <div className="reports-content-grid">
                                { dataSources.map((dataSource) => (
                                    <div className="report-card" key={dataSource.id}>
                                        <VscFilePdf className="icon clickable pink-icon" style={{ fontSize: 36, color: '#E80057FF' }} onClick={()=>handlePreview(dataSource)} />
                                        <div className="text-container">
                                            <div className="report-title clickable" onClick={()=>handlePreview(dataSource)}>{dataSource.name}</div>
                                            {/* <div className="report-date clickable" onClick={()=>handlePreview(dataSource.id)}>{dataSource.type}</div> */}
                                        </div>
                                        <div className="report-button-container">

                                            <DeleteOutlined
                                              onClick={()=>handleDelete(dataSource.id)}
                                              style={{
                                                    fontSize: iconSize,
                                                    color: colors.red,
                                                    marginTop: '0',
                                                    cursor: 'pointer',
                                                }}
                                            />
                                        </div>
                                    </div>

                                    // <div>
                                    //     <div className="settings-list-item" key={`user-${user.id}`}>
                                    //         <p className="list-item-title">{user.firstName}</p>
                                    //         <div className="options-container">
                                    //             <p className="permission-text">{user.permission}</p>
                                    //             <Link className="standard button" to={`user/${user.id}`}><EditTwoTone twoToneColor={colors.blue} style={{ fontSize: iconSize, padding: '10px' }} /></Link>
                                    //         </div>
                                    //     </div>
                                    // </div>

                                ))}
                            </div>

                        )
                    }
                </div>
            </div>
        </>
    );
};

export default ManageSourcesPage;
