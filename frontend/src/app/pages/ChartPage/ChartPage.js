import React, { Component, useState } from 'react';
import {
    Layout,
} from 'antd';
import { Redirect, Route, Switch } from 'react-router-dom';
import { AiOutlineUpload, CgFileDocument } from 'react-icons/all';
import { Header } from 'antd/es/layout/layout';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import Search from 'antd/es/input/Search';
import { reset } from 'enzyme/build/configuration';
import SideBar from '../../components/SideBar/SideBar';
import MapCard from '../../components/MapCard/MapCard';
import NetworkGraphCard from '../../components/NetworkGraph/NetworkGraphCard';
import '../../components/NetworkGraph/NetworkGraph.css';
import UserInfoCard from '../../components/UserInfoCard/UserInfoCard';
import SearchBar from '../../components/SearchBar/SearchBar';
import TimelineGraph from '../../components/TimelineGraph/TimelineGraph';
import PieChart from '../../components/PieChart/PieChart';
import '../../components/WordCloud/WordCloud.css';
import WordCloud from '../../components/WordCloud/WordCloud';
import 'rc-slider/assets/index.css';
import SimpleSection from '../../components/SimpleSection/SimpleSection';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import DraggableBarGraph from '../../components/DraggableBarGraph/DraggableBarGraph';
import OverviewSection from '../../components/OverviewSection/OverviewSection';
import OverviewGraphSection from '../../components/OverviewGraphSection/OverviewGraphSection';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import '../../components/UploadButton/UploadButton.css';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';
import UploadDataPage from '../UploadDataPage/UploadDataPage';
import ReportPreview from '../../components/ReportPreview/ReportPreview';
import { dataFromBackendAtom, userAtom } from '../../assets/userAtom';

const ChartPage = () => {
    const [showPdf, setShowPdf] = useState(false);
    const [isShowingPopup, setIsShowingPopup] = useState(false);
    const [searchLoading, setSearchLoading] = useState(false);
    const user = useRecoilValue(userAtom);
    const setDataFromBackEnd = useSetRecoilState(dataFromBackendAtom);

    const generateReport = () =>{
        setShowPdf(!showPdf);
    };

    const handleSearch = (value) =>{
        setSearchLoading(true);
        const jsonObj = {
            permission: user.permission,
            username: user.username,
        };

        const abortCont = new AbortController();
        const requestObj = {
            signal: abortCont.signal,
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(jsonObj),
        };
        const url = `http://localhost:9000/main/${value}`;
        fetch(url, requestObj)
            .then((res) =>{
                if (!res.ok) {
                    throw (res.error());
                }
                return res.json();
            })
            .then((data)=>{
                setSearchLoading(false);
                setDataFromBackEnd(data);
            })
            .catch((err) => {
                setSearchLoading(false);
                console.log(err.message);
            });
    };

    const showPopup = () =>{
        setIsShowingPopup(!isShowingPopup);
    };

    if (!user) return <Redirect to="/login" />;
    return (
        <>
            <Switch>
                <Route exact path="/chart">
                    {
                        isShowingPopup
                            ? (
                                <SimplePopup closePopup={showPopup}>
                                    <UploadDataPage />
                                </SimplePopup>
                            ) :
                            null
                    }
                    {
                        showPdf
                            ? (
                                <ReportPreview
                                  closePopup={generateReport}
                                  className="pdf"
                                  title="pdf-preview"
                                />
                            ) :
                            null
                    }
                    <Layout
                      id="outer_layout"
                      className="chart-page"
                    >
                        <SideBar currentPage="2" />
                        <Layout id="inner_layout_div">
                            <Header id="top_bar">
                                <Search
                                  placeholder="search..."
                                  onSearch={handleSearch}
                                  loading={searchLoading}
                                />

                                <button
                                  type="button"
                                  id="upload-btn"
                                  className="clickable"
                                  onClick={() => {
                                        showPopup(true);
                                    }}
                                >
                                    <AiOutlineUpload id="upload-btn-logo" />
                                    Upload
                                </button>

                                <button
                                  type="button"
                                  id="upload-btn"
                                  className="clickable"
                                  onClick={() => {
                                        generateReport();
                                    }}
                                >
                                    <CgFileDocument id="upload-btn-logo" />
                                    Generate Report
                                </button>

                                <UserInfoCard />
                            </Header>
                        </Layout>
                    </Layout>
                </Route>
            </Switch>
        </>
    );
};

export default ChartPage;
