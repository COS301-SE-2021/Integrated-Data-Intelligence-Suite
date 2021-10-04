import React, { Component, useState } from 'react';
import {
    Layout,
} from 'antd';
import { Redirect, Route, Switch } from 'react-router-dom';
import { AiOutlineUpload, CgFileDocument } from 'react-icons/all';
import { Header } from 'antd/es/layout/layout';
import {
 useRecoilState, useRecoilValue, useResetRecoilState, useSetRecoilState,
} from 'recoil';
import Search from 'antd/es/input/Search';
import { reset } from 'enzyme/build/configuration';
import { cloneDeep } from 'lodash';
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
import templateJson from '../../Mocks/messageMock.json';
import {
    backendDataState,
    currentPdfState,
    userState,
    totalLikedState,
    mostProminentSentimentState,
    numberOfTrendsState,
    numberOfAnomaliesState,
    averageInteractionState,
    overallSentimentState,
    engagementPerProvinceState,
    mapDataState,
    dataFrequencyState,
    wordCloudState,
    dominantWordsState,
    entitiesRelationshipsState,
    patternsRelationshipsState,
    anomaliesState, displayAnalyticsPdfState, isShowingUploadCSVPopupState,
} from '../../assets/AtomStore/AtomStore';

const ChartPage = () => {
    const [searchLoading, setSearchLoading] = useState(false);

    const [backendData, setBackendData] = useRecoilState(backendDataState);
    const [totalLikes, setTotalLikes] = useRecoilState(totalLikedState);
    const [mostProminentWords, setMostProminentWords] = useRecoilState(mostProminentSentimentState);
    const [numberOfTrends, setNumberOfTrends] = useRecoilState(numberOfTrendsState);
    const [numberOfAnomalies, setNumberOfAnomalies] = useRecoilState(numberOfAnomaliesState);
    const [averageInteraction, setAverageInteraction] = useRecoilState(averageInteractionState);
    const [overallSentiment, setOverallSentiment] = useRecoilState(overallSentimentState);
    const [engagementPerProvince, setEngagementPerProvince] = useRecoilState(engagementPerProvinceState);
    const [eapData, setMapData] = useRecoilState(mapDataState);
    const [dataFrequency, setDataFrequency] = useRecoilState(dataFrequencyState);
    const [wordCloud, setWordCloud] = useRecoilState(wordCloudState);
    const [dominantWords, setDominantWords] = useRecoilState(dominantWordsState);
    const [entitiesRelationship, setEntitiesRelationship] = useRecoilState(entitiesRelationshipsState);
    const [patternsRelationship, setPatternsRelationship] = useRecoilState(patternsRelationshipsState);
    const [anomalies, setAnomalies] = useRecoilState(anomaliesState);

    const [showCSV, setShowCSV] = useRecoilState(isShowingUploadCSVPopupState);
    const [showPdf, setShowPdf] = useRecoilState(displayAnalyticsPdfState);
    const [currentPdf, setCurrentPdf] = useRecoilState(currentPdfState);

    const user = useRecoilValue(userState);

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
                structureBackendData(data);
            })
            .catch((err) => {
                setSearchLoading(false);
                structureBackendData(templateJson);
                console.log(err.message);
            });
    };

    const structureBackendData = (data) => {
        setBackendData(data);
        setTotalLikes(data[0]);
        setMostProminentWords(data[1]);
        setNumberOfTrends(data[2]);
        setNumberOfAnomalies(data[3]);

        setAverageInteraction(data[4]);
        setOverallSentiment(data[5]);
        setEngagementPerProvince(data[6]);

        setMapData(data[7]);
        setDataFrequency(data[8]);

        setWordCloud(data[9]);
        setDominantWords(data[10]);

        setEntitiesRelationship(data[11]);
        setPatternsRelationship(data[12]);

        setAnomalies(data[13]);
        setCurrentPdf(data[data.length - 1][0]);
    };
    //
    if (!user) return <Redirect to="/login" />;
    return (
        <>
            <Switch>
                <Route exact path="/chart">
                    {
                        showCSV
                            ? (
                                <SimplePopup
                                  closePopup={()=> setShowCSV(false)}
                                  popupTitle="Upload File"
                                >
                                    <UploadDataPage
                                      handleTextChange={structureBackendData}
                                      isAnalyzeCSVPopupShowing={showCSV}
                                    />
                                </SimplePopup>
                            ) :
                            null
                    }
                    {
                        showPdf
                            ? (
                                <ReportPreview
                                  closePopup={() => setShowPdf(false)}
                                  className="pdf"
                                  title="pdf-preview"
                                  currentFile={currentPdf}
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
                                  onClick={() => setShowCSV(true)}
                                >
                                    <AiOutlineUpload id="upload-btn-logo" />
                                    Upload
                                </button>

                                <button
                                  type="button"
                                  id="upload-btn"
                                  className="clickable"
                                  onClick={() => setShowPdf(true)}
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
